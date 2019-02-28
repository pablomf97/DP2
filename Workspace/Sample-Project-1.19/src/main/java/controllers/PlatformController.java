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
import services.PlatformService;
import domain.Actor;
import domain.Platform;

@Controller
@RequestMapping("/platform")
public class PlatformController extends AbstractController {

	// Services

	@Autowired
	private PlatformService	platformService;
	
	@Autowired
	private ActorService actorService;
	
	// Display
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int platformId) {

		ModelAndView result;
		Platform platform;
		boolean isPrincipal = false;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		platform = this.platformService.findOne(platformId);
		
		if(platform.getBrotherhood().getId() == principal.getId())
			isPrincipal = true;

		result = new ModelAndView("platform/display");
		result.addObject("platform", platform);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("requestURI", "platform/display.do?processionId=" + platformId);

		return result;
	}

	// List 
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(required = false) Integer brotherhoodId) {
		ModelAndView result;
		Actor principal;
		Collection<Platform> platforms;
		
		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		platforms = this.platformService.findPlatformsByBrotherhoodId(principal.getId());

		String requestURI = "platform/list.do?memberId=" + principal.getId();
		result = new ModelAndView("platform/list");
		result.addObject("requestURI", requestURI);
		result.addObject("platforms", platforms);

		return result;
	}

	// Creation 
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Platform platform;

		platform = this.platformService.create();

		result = this.createEditModelAndView(platform);

		return result;

	}

	// Edition
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int platformId) {
		ModelAndView result;
		final Platform platform;

		platform = this.platformService.findOne(platformId);
		Assert.notNull(platform);
		result = this.createEditModelAndView(platform);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Platform platform, final BindingResult binding) {
		ModelAndView result;

		platform = this.platformService.reconstruct(platform, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(platform);
		else
			try {
				this.platformService.save(platform);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(platform, "platform.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Platform platform, final BindingResult binding) {
		ModelAndView result;
		
		platform = this.platformService.reconstruct(platform, binding);
		try {
			this.platformService.delete(platform);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(platform, "platform.commit.error");
		}

		return result;
	}

	//Ancillary methods
	protected ModelAndView createEditModelAndView(final Platform platform) {
		ModelAndView result;

		result = this.createEditModelAndView(platform, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Platform platform, final String messageCode) {
		final ModelAndView result;
		Actor principal;
		boolean isBrotherhood = false;
		boolean isPrincipal = false;

		
		principal = this.actorService.findByPrincipal();
		
		if(this.actorService.checkAuthority(principal, "BROTHERHOOD")) {
			isBrotherhood = true;
		}
		
		if(platform.getId() != 0 && platform.getBrotherhood().getId() == principal.getId())
			isPrincipal = true;

		result = new ModelAndView("platform/edit");
		result.addObject("platform", platform);
		result.addObject("isBrotherhood", isBrotherhood);
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("message", messageCode);

		return result;

	}
}
