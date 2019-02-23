package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import domain.Actor;
import domain.Enrolment;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController extends AbstractController {

	@Autowired
	private EnrolmentService enrolmentService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BrotherhoodService brotherhoodService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Locale locale) {
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

			res = new ModelAndView("enrolment/list");
			res.addObject("enrolments", enrolments);
			res.addObject("permission", permission);
			res.addObject("language", locale.getLanguage());
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("position/list");
			permission = false;

			res.addObject("errMsg", oopsie);
			res.addObject("permission", permission);
		}
		return res;
	}

}
