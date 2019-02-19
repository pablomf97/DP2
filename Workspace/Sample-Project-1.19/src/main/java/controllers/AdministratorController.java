/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView registerGET() {
		final Administrator a = this.administratorService.create();
		ModelAndView result;
		result = new ModelAndView("administrator/register");
		result.addObject("administrator", a);
		result.addObject("uri", "security/register.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView registerPOST(final Administrator adminForm, final BindingResult binding) {
		ModelAndView result;
		String emailError = "";
		Administrator admin;
		admin = this.administratorService.reconstruct(adminForm, binding);
		if (admin.getEmail() != null) {
			admin.setEmail(admin.getEmail().toLowerCase());
			emailError = this.actorService.checkEmail(admin.getEmail(), admin.getUserAccount().getAuthorities().iterator().next().getAuthority());
		}
		if (binding.hasErrors() || !emailError.isEmpty()) {
			result = new ModelAndView("administrator/register");
			result.addObject("uri", "security/register.do");
			admin.getUserAccount().setPassword("");
			result.addObject("admin", admin);
			result.addObject("emailError", emailError);
		} else
			try {
				admin.setPhoneNumber(this.actorService.checkSetPhoneCC(admin.getPhoneNumber()));
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(admin.getUserAccount().getPassword(), null);
				admin.getUserAccount().setPassword(hash);
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable opps) {
				result = new ModelAndView("brotherhood/register");
				result.addObject("uri", "security/register.do");
				result.addObject("messageCode", "actor.commit.error");
				result.addObject("emailError", emailError);
				admin.getUserAccount().setPassword("");
				result.addObject("admin", admin);
			}

		return result;
	}

}
