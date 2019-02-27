
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
import forms.BrotherhoodForm;

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
			final Collection<String> pictures = this.brotherhoodService.getSplitPictures(b.getPictures());
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
		BrotherhoodForm bf;
		bf = this.brotherhoodService.createForm();
		if (id == 0) {
			result = new ModelAndView("brotherhood/edit");
			result.addObject("brotherhoodForm", bf);
		} else
			try {
				result = new ModelAndView("brotherhood/edit");
				b = this.brotherhoodService.findOne(id);
				Assert.isTrue(b.equals(this.actorService.findByPrincipal()));
				bf.setId(b.getId());
				result.addObject("brotherhoodForm", bf);
				result.addObject("brotherhood", b);
				result.addObject("uri", "brotherhood/edit.do");
				final Collection<String> pictures = this.brotherhoodService.getSplitPictures(b.getPictures());
				result.addObject("pictures", pictures);

			} catch (final Throwable opps) {
				//TODO: ver la posibilidada de una pantalla de error
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPOST(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		String check = "";
		String passW = "";
		String uniqueUsername = "";
		Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.reconstruct(brotherhoodForm, binding);
		if (brotherhood.getId() == 0) {
			passW = this.actorService.checkPass(brotherhoodForm.getPassword(), brotherhoodForm.getPassword2());
			uniqueUsername = this.actorService.checkUniqueUser(brotherhoodForm.getUsername());
			check = this.actorService.checkLaw(brotherhoodForm.getCheckBox());
		}
		final Collection<String> pictures = this.brotherhoodService.getSplitPictures(brotherhood.getPictures());

		brotherhood.setEmail(brotherhood.getEmail().toLowerCase());
		emailError = this.actorService.checkEmail(brotherhood.getEmail(), brotherhood.getUserAccount().getAuthorities().iterator().next().getAuthority());
		if (binding.hasErrors() || !emailError.isEmpty() || !check.isEmpty() || !passW.isEmpty() || !uniqueUsername.isEmpty()) {
			result = new ModelAndView("brotherhood/edit");
			result.addObject("uri", "brotherhood/edit.do");
			brotherhood.getUserAccount().setPassword("");
			result.addObject("brotherhood", brotherhood);
			result.addObject("emailError", emailError);
			result.addObject("checkLaw", check);
			result.addObject("checkPass", passW);
			result.addObject("uniqueUsername", uniqueUsername);
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
