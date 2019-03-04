package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.SocialProfile;

import services.ActorService;
import services.SocialProfileService;

@Controller
@RequestMapping("/socialProfile/actor")
public class SocialProfileController extends AbstractController {


	// Services

	@Autowired
	private ActorService actorService;

	@Autowired
	private SocialProfileService socialProfileService;

	// Constructors

	public SocialProfileController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		res = this.createEditModelAndView(socialProfile);

		return res;
	}

	// Display with no parameters
	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView res;
		Actor principal;

		Collection<SocialProfile> socialProfiles;
		Boolean editable = true;

		principal = this.actorService.findByPrincipal();



		
		socialProfiles = this.socialProfileService.findAll();//this.socialProfileService.socialProfilesByUser(principal.getUserAccount().getUsername());


		res = new ModelAndView("socialProfile/list");
		res.addObject("editable", editable);
		res.addObject("socialProfiles", socialProfiles);
		res.addObject("principal", principal);

		return res;
	}
	@RequestMapping(value = "/edit", params = "socialprofileID")
	public ModelAndView display(@RequestParam int socialprofileID) {
		ModelAndView res;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(socialprofileID);
		Assert.notNull(socialProfile);
		Assert.isTrue(this.socialProfileService.checkifPrincipalIsOwnerBySocialProfileId(socialprofileID),"not.allowed");
		res = this.createEditModelAndView(socialProfile);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialProfile socialProfile,
			BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createEditModelAndView(socialProfile);
		} else {
			try {
				
				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(socialProfile,
						"system.commit.error");
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid SocialProfile socialProfile,
			BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createEditModelAndView(socialProfile);
		} else {
			try {
				Actor actor = this.actorService.findByPrincipal();
				this.socialProfileService.delete(socialProfile);
				res = new ModelAndView("redirect:list.do"
						+ actor.getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(socialProfile,
						"system.commit.error");
			}
		}
		return res;
	}


	///--------ModelAndView

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile) {
		ModelAndView res;

		res = this.createEditModelAndView(socialProfile, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile,
			String messageCode) {
		ModelAndView res;
		Boolean editable;
		Actor principal;
		principal=this.actorService.findByPrincipal();

		editable = this.socialProfileService
				.checkifPrincipalIsOwnerBySocialProfileId(socialProfile.getId());

		res = new ModelAndView("socialProfile/edit");
		res.addObject("principal",principal);
		res.addObject("editable", editable);
		res.addObject("socialProfile", socialProfile);
		res.addObject("message", messageCode);
		return res;
	}











}
