//
//package controllers.handyWorker;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.Assert;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import services.PlatformService;
//import domain.Brotherhood;
//import domain.Platform;
//
//@Controller
//@RequestMapping("/platform/brotherhood")
//public class PlatformController extends AbstractController {
//
//	// Services
//
//	@Autowired
//	private PlatformService	platformService;
//	
//	@Autowired
//	private BrotherhoodService	brotherhoodService;
//
//	// List 
//	@RequestMapping(value = "/list")
//	public ModelAndView list(@RequestParam int brotherhoodId) {
//		ModelAndView result;
//		Brotherhood principal;
//		Collection<Brotherhood> brotherhoods = null;
//		
//		principal = this.brotherhoodService.findByPrincipal();
//
//		res = new ModelAndView("brotherhood/list");
//		res.addObject("brotherhoods", brotherhoods);
//		res.addObject("principal", principal);
//
//		return res;
//	}
//
//	// Creation 
//	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView create() {
//		final ModelAndView result;
//		Platform platform;
//
//		platform = this.platformService.create();
//
//		result = this.createEditModelAndView(platform);
//
//		return result;
//
//	}
//
//	// Edition
//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int platformId) {
//		ModelAndView result;
//		final Platform platform;
//
//		platform = this.platformService.findOne(platformId);
//		Assert.notNull(platform);
//		result = this.createEditModelAndView(platform);
//
//		return result;
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
//	public ModelAndView save(@Valid final Platform platform, final BindingResult binding) {
//		ModelAndView result;
//
//		if (binding.hasErrors())
//			result = this.createEditModelAndView(platform);
//		else
//			try {
//				this.platformService.save(platform);
//				result = new ModelAndView("redirect:brotherhood/display.do");
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(platform, "mr.commit.error");
//			}
//
//		return result;
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
//	public ModelAndView delete(final Platform platform, final BindingResult binding) {
//		ModelAndView result;
//
//		try {
//			this.platformService.delete(platform);
//			result = new ModelAndView("redirect:brotherhood/display.do");
//		} catch (final Throwable oops) {
//			result = this.createEditModelAndView(platform, "mr.commit.error");
//		}
//
//		return result;
//	}
//
//	//Ancillary methods
//	protected ModelAndView createEditModelAndView(final Platform platform) {
//		ModelAndView result;
//
//		result = this.createEditModelAndView(platform, null);
//
//		return result;
//	}
//
//	protected ModelAndView createEditModelAndView(final Platform platform, final String messageCode) {
//		final ModelAndView result;
//		Brotherhood principal;
//		boolean isBrotherhood = false;
//		
//		principal = this.brotherhoodService.findByPrincipal();
//		if(principal != null) {
//			isBrotherhood = true;
//		}
//
//		result = new ModelAndView("platform/edit");
//		result.addObject("platform", platform);
//		result.addObject("isBrotherhood", isBrotherhood);
//
//		result.addObject("message", messageCode);
//
//		return result;
//
//	}
//
//}
