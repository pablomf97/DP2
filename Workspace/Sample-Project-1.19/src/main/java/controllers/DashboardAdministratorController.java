package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MarchService;
import services.MemberService;

@Controller
@RequestMapping(value="statistics/administrator")
public class DashboardAdministratorController extends AbstractController{
	
	//Services
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private MarchService marchService;
	
	//Display
	
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(){
		final ModelAndView result;
		
		result = new ModelAndView("administrator/statistics");
		
		result.addObject("requestURI", "statistics/administrator/display.do");
		return result;
		
	}
}
