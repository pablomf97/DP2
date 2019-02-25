package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.PositionService;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping("/enrolment")
public class EnrollmentController extends AbstractController {

	// Services

	@Autowired
	private EnrolmentService enrolmentService;

	@Autowired
	private ActorService actorService;

	// @Autowired
	// private MemberService memberService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@Autowired
	private PositionService positionService;

	// Constructor

	public EnrollmentController() {
		super();
	}

	// Methods

	/* List of enrollments of a member */
	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView listMember(Locale locale) {
		ModelAndView res;
		Actor principal;
		Collection<Enrolment> enrolments;
		Boolean permission;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "MEMBER"));

			enrolments = this.enrolmentService.getEnrollmentsByMember(principal
					.getId());

			permission = true;

			res = new ModelAndView("enrolment/listMember");
			res.addObject("enrolments", enrolments);
			res.addObject("permission", permission);
			res.addObject("language", locale.getLanguage());
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("enrolment/listMember");
			permission = false;

			res.addObject("errMsg", oopsie);
			res.addObject("permission", permission);
		}
		return res;
	}

	/* List of enrollments of a brotherhood */
	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listBrotherhood(Locale locale) {
		ModelAndView res;
		Actor principal;
		Collection<Enrolment> enrolments;
		Boolean permission;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"BROTHERHOOD"));

			enrolments = this.enrolmentService
					.getEnrollmentsByBrotherhood(principal.getId());

			permission = true;

			res = new ModelAndView("enrolment/listBrotherhood");
			res.addObject("enrolments", enrolments);
			res.addObject("permission", permission);
			res.addObject("language", locale.getLanguage());
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("enrolment/listBrotherhood");
			permission = false;

			res.addObject("errMsg", oopsie);
			res.addObject("permission", permission);
		}
		return res;
	}

	/* Request for an enrollment */
	@RequestMapping(value = "/member/request", method = RequestMethod.GET)
	public ModelAndView request(@RequestParam int brotherhoodID) {
		ModelAndView res;
		Actor principal;
		Enrolment enrolment;
		Brotherhood brotherhood;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal, "MEMBER"));

			enrolment = this.enrolmentService.create();

			brotherhood = this.brotherhoodService.findOne(brotherhoodID);

			enrolment.setMoment(new Date(System.currentTimeMillis() - 1));
			enrolment.setBrotherhood(brotherhood);
			enrolment.setMember((Member) principal);

			this.enrolmentService.save(enrolment);

			res = new ModelAndView("redirect:/enrolment/member/list.do");

		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			// TODO Cambiar cuando tenga lo de chema
			res = new ModelAndView("redirect:/enrolment/member/list.do");
		}
		return res;
	}

	/* Accept or reject an enrollment */
	@RequestMapping(value = "/brotherhood/action", method = RequestMethod.GET)
	public ModelAndView actionsEnrolments(@RequestParam String action,
			@RequestParam int enrolmentID, Locale locale) {
		ModelAndView res;
		Actor principal;
		Enrolment enrolment;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"BROTHERHOOD"));

			enrolment = this.enrolmentService.findOne(enrolmentID);

			Assert.isTrue(enrolment.getBrotherhood().getId() == principal
					.getId());

			if (action.equals("accept")) {

				res = this.createEditModelAndView(enrolment, locale);

			} else if (action.equals("reject")) {

				// TODO isout null
				if (!enrolment.getIsOut()) {
					enrolment.getDropOutMoment().add(
							new Date(System.currentTimeMillis() - 1));
				}

				enrolment.setIsOut(true);
				enrolment.setPosition(null);
				this.enrolmentService.save(enrolment);
				// TODO Cambiar cuando tenga lo de chema
				res = new ModelAndView(
						"redirect:/enrolment/brotherhood/list.do");

			} else {

				res = new ModelAndView("misc/403");

			}
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			// TODO Cambiar cuando tenga lo de chema
			res = new ModelAndView("redirect:/enrolment/member/list.do");
		}
		return res;
	}

	/* Saving an enrollment */
	@RequestMapping(value = "/brotherhood/edit.do", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Enrolment enrolment,
			@RequestParam("position") String position, BindingResult binding,
			Locale locale) {

		ModelAndView res;
		Actor principal;

		enrolment = this.enrolmentService.reconstruct(enrolment, position,
				binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(enrolment, binding.toString(),
					locale);
		} else {
			try {
				principal = this.actorService.findByPrincipal();
				Assert.isTrue(this.actorService.checkAuthority(principal,
						"BROTHERHOOD"));

				Assert.isTrue(enrolment.getBrotherhood().getId() == principal
						.getId());

				this.enrolmentService.save(enrolment);

				res = new ModelAndView("redirect:list.do");
			} catch (IllegalArgumentException oops) {
				res = new ModelAndView("misc/403");
			} catch (Throwable oopsie) {
				res = this.createEditModelAndView(enrolment,
						binding.toString(), locale);
			}
		}

		return res;
	}

	// Manage methods
	protected ModelAndView createEditModelAndView(Enrolment enrolment,
			Locale locale) {
		ModelAndView res;

		res = createEditModelAndView(enrolment, null, locale);

		return res;
	}

	protected ModelAndView createEditModelAndView(Enrolment enrolment,
			String messageCode, Locale locale) {
		ModelAndView res;

		res = new ModelAndView("enrolment/edit");
		res.addObject("enrolment", enrolment);
		res.addObject("message", messageCode);
		res.addObject("positions", this.positionService.findAll());
		res.addObject("language", locale.getLanguage());
		return res;
	}
}
