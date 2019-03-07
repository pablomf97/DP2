/*
 * CustomerController.java
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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import forms.ActorForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("customer/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}


	public class ActorController extends AbstractController {

		@Autowired
		private ActorService	actorService;


		// Constructors -----------------------------------------------------------

		public ActorController() {
			super();
		}

		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView displayGET(@RequestParam final int id) {
			ModelAndView result;
			Actor a;
			try {
				result = new ModelAndView("administrator/display");
				a = this.actorService.findOne(id);
				Assert.isTrue(a.equals(this.actorService.findByPrincipal()));
				result.addObject("administrator", a);
			} catch (final Throwable opps) {
				//TODO: ver la posibilidada de una pantalla de error
				result = new ModelAndView("redirect:/welcome/index.do");

			}

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView editGET(@RequestParam final int id) {
			ModelAndView result;
			Actor a;
			ActorForm af;
			af = this.actorService.createForm();
			if (id == 0) {
				result = new ModelAndView("administrator/edit");
				a = this.actorService.create();
				a.setId(0);
				result.addObject("administrator", a);
				result.addObject("administratorForm", af);
			} else
				try {
					result = new ModelAndView("administrator/edit");
					a = this.actorService.findOne(id);
					Assert.isTrue(a.equals(this.actorService.findByPrincipal()));
					af.setId(a.getId());
					result.addObject("administratorForm", af);
					result.addObject("administrator", a);
					result.addObject("uri", "administrator/edit.do");

				} catch (final Throwable opps) {
					//TODO: ver la posibilidada de una pantalla de error
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST)
		public ModelAndView editPOST(final ActorForm actorForm, final BindingResult binding) {
			ModelAndView result;
			String passW = "";
			String uniqueUsername = "";
			Actor actor;
			try {
				actor = this.actorService.reconstruct(actorForm, binding);
				if (actor.getId() == 0) {
					passW = this.actorService.checkPass(actorForm.getPassword(), actorForm.getPassword2());
					uniqueUsername = this.actorService.checkUniqueUser(actorForm.getUsername());
				}
				if (binding.hasErrors() || !passW.isEmpty() || !uniqueUsername.isEmpty()) {
					result = new ModelAndView("customer/edit");
					result.addObject("uri", "customer/edit.do");
					actor.getUserAccount().setPassword("");
					result.addObject("actor", actor);
					result.addObject("checkPass", passW);
					result.addObject("uniqueUsername", uniqueUsername);
				} else
					try {
						final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
						final String hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
						actor.getUserAccount().setPassword(hash);
						this.actorService.save(actor);
						result = new ModelAndView("redirect:/welcome/index.do");
					} catch (final Throwable opps) {
						result = new ModelAndView("customer/edit");
						result.addObject("uri", "customer/edit.do");
						result.addObject("messageCode", "actor.commit.error");
						actor.getUserAccount().setPassword("");
						result.addObject("actor", actor);
					}
			} catch (final Throwable opps) {
				//TODO: pantalla de error
				result = new ModelAndView("redirect:/welcome/index.do");
			}
			return result;
		}

	}

}
