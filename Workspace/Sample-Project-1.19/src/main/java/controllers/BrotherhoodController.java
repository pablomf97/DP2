
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ActorService		actorService;


	public BrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView registerGET() {
		final Brotherhood b = this.brotherhoodService.create();
		ModelAndView result;
		result = new ModelAndView("brotherhood/register");
		result.addObject("brotherhood", b);
		result.addObject("uri", "security/register.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView registerPOST(final Brotherhood brotherhoodForm, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.reconstruct(brotherhoodForm, binding);
		if (brotherhood.getEmail() != null) {
			brotherhood.setEmail(brotherhood.getEmail().toLowerCase());
			emailError = this.actorService.checkEmail(brotherhood.getEmail(), brotherhood.getUserAccount().getAuthorities().iterator().next().getAuthority());
		}
		if (binding.hasErrors() || !emailError.isEmpty()) {
			result = new ModelAndView("brotherhood/register");
			result.addObject("uri", "security/register.do");
			brotherhood.getUserAccount().setPassword("");
			result.addObject("brotherhood", brotherhood);
			result.addObject("emailError", emailError);
		} else
			try {
				brotherhood.setPhoneNumber(this.actorService.checkSetPhoneCC(brotherhood.getPhoneNumber()));
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(brotherhood.getUserAccount().getPassword(), null);
				brotherhood.getUserAccount().setPassword(hash);
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("brotherhood/register");
				result.addObject("uri", "security/register.do");
				result.addObject("messageCode", "actor.commit.error");
				result.addObject("emailError", emailError);
				brotherhood.getUserAccount().setPassword("");
				result.addObject("brotherhood", brotherhood);
			}

		return result;
	}

}
