//
//package controllers.handyWorker;
//
//import java.util.Collection;
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
//import services.ProcessionService;
//import domain.Brotherhood;
//import domain.Member;
//import domain.Procession;
//
//@Controller
//@RequestMapping("/procession/brotherhood")
//public class ProcessionController extends AbstractController {
//
//	// Services
//
//	@Autowired
//	private ProcessionService	processionService;
//
//	@Autowired
//	private BrotherhoodService	brotherhoodService;
//	
//	@Autowired
//	private MemberService	memberService;
//
//	//List
//
//	@RequestMapping(value = "/member,brotherhood/list")
//	public ModelAndView list(@RequestParam(required = false) Integer memberId, @RequestParam(required = false) Integer brotherhoodId) {
//		ModelAndView result;
//		Collection<Procession> processions;
//
//		try {
//			Brotherhood principal;
//
//			principal = this.brotherhoodService.findByPrincipal();
//			processions = this.brotherhoodService.findAllProcessionsByBrotherhood(principal.getId());
//			
//			String requestURI = "procession/customer,handyworker/list.do?brotherhoodId=" + brotherhoodId;
//			result = new ModelAndView("procession/list");
//			result.addObject("requestURI", requestURI);
//			result.addObject("processions", processions);
//
//			return result;
//
//		} catch (Throwable oops) {
//			Member principal;
//			
//			principal = this.memberService.findByPrincipal();
//			processions = this.memberService.findAllProcessionsByMember(principal.getId());
//			
//			String requestURI = "procession/customer,handyworker/list.do?memberId="	+ memberId;
//			result = new ModelAndView("procession/list");
//			result.addObject("requestURI", requestURI);
//			result.addObject("processions", processions);
//
//			return result;
//		}
//	}
//	
//	// Creation 
//	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView create() {
//		final ModelAndView result;
//		Procession procession;
//
//		procession = this.processionService.create();
//
//		result = this.createEditModelAndView(procession);
//
//		return result;
//
//	}
//
//	// Edition
//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int processionId) {
//		ModelAndView result;
//		final Procession procession;
//
//		procession = this.processionService.findOne(processionId);
//		Assert.notNull(procession);
//		result = this.createEditModelAndView(procession);
//
//		return result;
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
//	public ModelAndView save(@Valid final Procession procession, final BindingResult binding) {
//		ModelAndView result;
//
//		if (binding.hasErrors())
//			result = this.createEditModelAndView(procession);
//		else
//			try {
//				this.processionService.save(procession);
//				result = new ModelAndView("redirect:brotherhood/display.do");
//			} catch (final Throwable oops) {
//				result = this.createEditModelAndView(procession, "mr.commit.error");
//			}
//
//		return result;
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
//	public ModelAndView delete(final Procession procession, final BindingResult binding) {
//		ModelAndView result;
//
//		try {
//			this.processionService.delete(procession);
//			result = new ModelAndView("redirect:brotherhood/display.do");
//		} catch (final Throwable oops) {
//			result = this.createEditModelAndView(procession, "mr.commit.error");
//		}
//
//		return result;
//	}
//
//	//Ancillary methods
//	protected ModelAndView createEditModelAndView(final Procession procession) {
//		ModelAndView result;
//
//		result = this.createEditModelAndView(procession, null);
//
//		return result;
//	}
//
//	protected ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
//		final ModelAndView result;
//		Brotherhood principal;
//		boolean isBrotherhood = false;
//		
//		principal = this.brotherhoodService.findByPrincipal();
//		if(principal != null) {
//			isBrotherhood = true;
//		}
//				
//		result = new ModelAndView("procession/edit");
//		result.addObject("procession", procession);
//		result.addObject("isBrotherhood", isBrotherhood);
//		
//		result.addObject("message", messageCode);
//
//		return result;
//
//	}
//
//}
