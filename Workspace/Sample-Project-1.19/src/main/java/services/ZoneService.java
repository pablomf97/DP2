package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ZoneRepository;
import domain.Actor;
import domain.Administrator;
import domain.Zone;


@Service
@Transactional
public class ZoneService {
	
	//Supporting Services ----------------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	//Managed repository----------------------------------------------------------
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	//CRUD Methods ---------------------------------------------------------------------
	//Create ------------------------------------------------------
	
	public Zone create(){
		
		final Zone result;
		final Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Administrator);
		
		result = new Zone();
		
		return result;
		
	}
	
	//Save ------------------------------------------------------
	
	public Zone save(final Zone zone){
		
		final Zone result;
		final Zone saved;
		final Actor principal;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Administrator);
		
		Assert.notNull(zone);
		
		
		result = new Zone();
		result.setName(zone.getName());
		result.setPictures(zone.getPictures());
		
		saved = this.zoneRepository.save(result);
		
		return saved;
		
	}
	
	//Delete ------------------------------------------------------
	
	public void delete(final Zone zone){
		
		Actor principal;
		Collection<Zone> selectedZones;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal instanceof Administrator);
		
		selectedZones = this.findSelectedZones();
		Assert.notEmpty(selectedZones);
		Assert.isTrue(!(selectedZones.contains(zone)));
		
		this.zoneRepository.delete(zone);
		
	}
	
	//Finds --------------------------------------------
	
	public Zone findOne(int id){
		final Zone result;
		
		result = this.zoneRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<Zone>findAll(){
		final Collection<Zone> result;
		
		result = this.zoneRepository.findAll();
		Assert.notEmpty(result);
		
		return result;
		
	}
	
	public Collection<Zone> findSelectedZones(){
		final Collection<Zone>result;
		
		result = this.zoneRepository.findSelectedZones();
		Assert.notNull(result);
		
		return result;
	}
	
}
