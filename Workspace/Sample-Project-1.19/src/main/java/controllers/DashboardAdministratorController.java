package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MarchService;
import services.MemberService;
import services.ProcessionService;
import domain.Brotherhood;
import domain.Member;
import domain.Procession;

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
	
	@Autowired
	private ProcessionService processionService;
	
	//Display
	
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(){
		
		final ModelAndView result;
		
		Double averageMemberPerBrotherhood;
		Double minMemberPerBrotherhood;
		Double maxMemberPerBrotherhood;
		Collection<Member> acceptedMembers;
		
		Brotherhood largestBrotherhood;
		Brotherhood smallestBrotherhood;
		
		Double ratioApprovedRequests;
		Double ratioRejectedRequests;
		Double ratioPendingRequests;
		List<Double> ratioApprovedRequestsInAProcession;
		
		Collection<Procession> earlyProcessions;
		
		
		averageMemberPerBrotherhood = this.memberService.averageMemberPerBrotherhood();
		minMemberPerBrotherhood = this.memberService.minMemberPerBrotherhood();
		maxMemberPerBrotherhood = this.memberService.maxMemberPerBrotherhood();
		acceptedMembers = this.memberService.acceptedMembers();
		
		largestBrotherhood = this.brotherhoodService.largestBrotherhood();
		smallestBrotherhood = this.brotherhoodService.smallestBrotherhood();
		
		ratioApprovedRequests = this.marchService.ratioApprovedRequests();
		ratioRejectedRequests = this.marchService.ratioRejectedRequests();
		ratioPendingRequests = this.marchService.ratioPendingRequests();
		ratioApprovedRequestsInAProcession = this.marchService.ratioApprovedInAProcession();
		
		earlyProcessions = this.processionService.findEarlyProcessions();
		
		result = new ModelAndView("administrator/statistics");
		
		result.addObject("earlyProcessions", earlyProcessions);
		
		result.addObject("ratioApprovedRequests", ratioApprovedRequests);
		result.addObject("ratioRejectedRequests", ratioRejectedRequests);
		result.addObject("ratioPendingRequests", ratioPendingRequests);
		result.addObject("ratioApprovedProcession", ratioApprovedRequestsInAProcession);
		
		result.addObject("largestBrotherhood", largestBrotherhood);
		result.addObject("smallestBrotherhood", smallestBrotherhood);
		
		result.addObject("acceptedMembers", acceptedMembers);
		result.addObject("maxMemberPerBrotherhood", maxMemberPerBrotherhood);
		result.addObject("minMemberPerBrotherhood", minMemberPerBrotherhood);
		result.addObject("averageMemberPerBrotherhood", averageMemberPerBrotherhood);
		
		result.addObject("requestURI", "statistics/administrator/display.do");
		return result;
		
	}
}
