
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MarchService;
import domain.Actor;
import domain.Brotherhood;
import domain.March;
import domain.Member;

@Controller
@RequestMapping("/march")
public class MarchController extends AbstractController {

	// Services

	@Autowired
	private MarchService	marchService;
	
	@Autowired
	private ActorService actorService;

	// Display
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int marchId) {

		ModelAndView result;
		March march;

		march = this.marchService.findOne(marchId);

		result = new ModelAndView("march/display");
		result.addObject("march", march);
		result.addObject("requestURI", "march/display.do");

		return result;
	}
	
	// List 
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam int memberId) {
		ModelAndView result;
		Actor principal;
		Member member;
		Collection<March> marchs;
		
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "MEMBER"), "not.allowed");
		
		member = (Member) principal;
		
		marchs = this.marchService.findMarchsByMemberId(member.getId());

		result = new ModelAndView("brotherhood/list");
		result.addObject("marchs", marchs);
		result.addObject("principal", principal);

		return result;
	}

	// Creation 
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		March march;

		march = this.marchService.create();

		result = this.createEditModelAndView(march);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int marchId) {
		ModelAndView result;
		final March march;

		march = this.marchService.findOne(marchId);
		Assert.notNull(march);
		result = this.createEditModelAndView(march);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(March march, final BindingResult binding) {
		ModelAndView result;

		march = this.marchService.reconstruct(march, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(march);
		else
			try {
				this.marchService.save(march);
				result = new ModelAndView("redirect:brotherhood/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(march, "mr.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final March march, final BindingResult binding) {
		ModelAndView result;

		try {
			this.marchService.delete(march);
			result = new ModelAndView("redirect:brotherhood/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(march, "mr.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final March march) {
		ModelAndView result;

		result = this.createEditModelAndView(march, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final March march, final String messageCode) {
		final ModelAndView result;
		Actor principal;
		boolean isBrotherhood = false;
		
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		if(this.actorService.checkAuthority(principal, "BROTHERHOOD")) {
			isBrotherhood = true;
		}

		result = new ModelAndView("platform/edit");
		result.addObject("march", march);
		result.addObject("isBrotherhood", isBrotherhood);

		result.addObject("message", messageCode);

		return result;

	}

}
