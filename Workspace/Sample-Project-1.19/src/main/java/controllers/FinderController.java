package controllers;


import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Finder;
import domain.Member;
import domain.Procession;

import services.FinderService;
import services.MemberService;



@Controller
@RequestMapping("/finder/member")
public class FinderController extends AbstractController{
	// Services

	@Autowired
	private FinderService finderService;

	@Autowired
	private MemberService memberService;
	

	// Constructors

	public FinderController() {
		super();
	}

	///list

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;
		
		Member principal;

		principal = this.memberService.findByPrincipal();
		finder = principal.getFinder();
		 
		Collection<Procession> processions = finder.getSearchResults();
	
		
		result = new ModelAndView("finder/list");
		result.addObject("processions", processions);
		result.addObject("requestUri", "finder/member/list.do");

		return result;
	}

	//search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;
		Member principal;

		principal = this.memberService.findByPrincipal();
		
			finder = principal.getFinder();
		
		
		
		result=new ModelAndView("finder/search");
		result.addObject("finder",finder);
		result.addObject("requestUri", "finder/member/search.do");
		return result;
	}
	//DELETE
	@RequestMapping(value="/edit",method=RequestMethod.POST,params="delete")
	public ModelAndView delete(final Finder finder,final BindingResult binding) {
		ModelAndView result;

		try {
			this.finderService.delete(finder);
			result = new ModelAndView("redirect:search.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder,
					"finder.commit.error");
		}

		return result;
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(@Valid final Finder finder,
			final BindingResult binding) {
		ModelAndView result;



		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				System.out.println(e.toString());
			result = this.createEditModelAndView(finder);

		} else
			try {
				
				this.finderService.search(finder);
					result = new ModelAndView(
							"redirect:/finder/member/list.do");


			} catch (final Throwable oops) {
				System.out.println(finder.getSearchResults());
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.createEditModelAndView(finder, oops.getMessage());
			}


		return result;
	}
	
	//ancillary methods
	
	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Finder finder,
			final String messageCode) {
		ModelAndView result;
		final Collection<Procession> processions;
		processions = finder.getSearchResults();
		

		result = new ModelAndView("finder/search");
		result.addObject("message", messageCode);
		result.addObject("finder", finder);
		result.addObject("processions", processions);

		return result;
	}


}
