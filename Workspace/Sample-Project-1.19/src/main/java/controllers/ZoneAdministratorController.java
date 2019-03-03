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

import services.ActorService;
import services.ZoneService;
import domain.Actor;
import domain.Zone;

@Controller
@RequestMapping("zone/administrator")
public class ZoneAdministratorController extends AbstractController{

	//Services -----------------------------------------------------------

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private ActorService actorService;


	//Create -----------------------------------------------------------

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){

		final ModelAndView result;
		final Zone zone;

		zone = this.zoneService.create();
		Assert.notNull(zone);

		result = this.createEditModelAndView(zone);

		return result;


	}

	//List ---------------------------------------------------------------

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		final ModelAndView result;
		Collection<Zone> zones;

		zones = this.zoneService.findAll();
		Assert.notEmpty(zones);

		result = new ModelAndView("zone/list");
		result.addObject("zones", zones);

		return result;
	}

	//Edit

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int zoneId){
		final ModelAndView result;
		final Zone zone;

		zone = this.zoneService.findOne(zoneId);
		Assert.notNull(zone);

		result = this.createEditModelAndView(zone);

		return result;
	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid final Zone zone, final BindingResult binding){

		ModelAndView result;

		if(binding.hasErrors())
			result = this.createEditModelAndView(zone);
		else
			try{
				this.zoneService.save(zone);
				result = new ModelAndView("redirect:list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(zone, "zone.commit.error");
			}


		return result;
	}


	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid final Zone zone, final BindingResult binding){

		ModelAndView result;

		try{
			this.zoneService.delete(zone);
			result = new ModelAndView("redirect:list.do");
		}catch(final Throwable oops){
			result = this.createEditModelAndView(zone, "zone.commit.error");
		}


		return result;
	}
	
	//Ancillary methods ------------------------------------------------

	public ModelAndView createEditModelAndView(final Zone zone){

		ModelAndView result;

		result = this.createEditModelAndView(zone, null);

		return result;


	}

	public ModelAndView createEditModelAndView(final Zone zone, String messageCode){

		Actor principal;
		final String name;
		final String pictures;
		final ModelAndView result;
		final Collection<Zone> selectedZones;
		boolean isSelected = false;
		boolean permission = false;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);

		name = zone.getName();
		pictures = zone.getPictures();
		selectedZones = this.zoneService.findSelectedZones();
		Assert.notNull(selectedZones);
		
		if(selectedZones.contains(zone)){
			isSelected = true;
		}
		
		if(this.actorService.checkAuthority(principal, "ADMINISTRATOR")){
			
			permission = true;
		}
		
		result = new ModelAndView("zone/edit");
		result.addObject("principal", principal);
		result.addObject("name", name);
		result.addObject("pictures", pictures);
		result.addObject("zone", zone);
		result.addObject("isSelected", isSelected);
		result.addObject("permission", permission);
		return result;

	}
	
}
