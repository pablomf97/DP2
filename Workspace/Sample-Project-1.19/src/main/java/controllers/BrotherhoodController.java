
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayGET(@RequestParam final int id) {
		ModelAndView result;
		Brotherhood b;
		try {
			result = new ModelAndView("brotherhood/display");
			b = this.brotherhoodService.findOne(id);
			Assert.isTrue(b.equals(this.actorService.findByPrincipal()));
			final Collection<String> pictures = this.brotherhoodService.getPictures(b.getPictures());
			result.addObject("brotherhood", b);
			result.addObject("pictures", pictures);

		} catch (final Throwable opps) {
			//TODO: ver la posibilidada de una pantalla de error
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam final int id) {
		ModelAndView result;
		Brotherhood b;
		if (id == 0) {
			result = new ModelAndView("brotherhood/edit");
			b = this.brotherhoodService.create();
			result.addObject("brotherhood", b);
		} else
			try {
				result = new ModelAndView("brotherhood/edit");
				b = this.brotherhoodService.findOne(id);
				Assert.isTrue(b.equals(this.actorService.findByPrincipal()));
				result.addObject("brotherhood", b);
				result.addObject("uri", "brotherhood/edit.do");
				final Collection<String> pictures = this.brotherhoodService.getPictures(b.getPictures());
				result.addObject("pictures", pictures);

			} catch (final Throwable opps) {
				//TODO: ver la posibilidada de una pantalla de error
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPOST(Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		brotherhood = this.brotherhoodService.reconstruct(brotherhood, binding);
		final Collection<String> pictures = this.brotherhoodService.getPictures(brotherhood.getPictures());
		if (brotherhood.getEmail() != null) {
			brotherhood.setEmail(brotherhood.getEmail().toLowerCase());
			emailError = this.actorService.checkEmail(brotherhood.getEmail(), brotherhood.getUserAccount().getAuthorities().iterator().next().getAuthority());
		}
		if (binding.hasErrors() || !emailError.isEmpty()) {
			result = new ModelAndView("brotherhood/edit");
			result.addObject("uri", "brotherhood/edit.do");
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
				result = new ModelAndView("brotherhood/edit");
				result.addObject("uri", "brotherhood/edit.do");
				result.addObject("messageCode", "actor.commit.error");
				result.addObject("emailError", emailError);
				brotherhood.getUserAccount().setPassword("");
				result.addObject("brotherhood", brotherhood);
			}
		result.addObject("pictures", pictures);
		return result;
	}

}
