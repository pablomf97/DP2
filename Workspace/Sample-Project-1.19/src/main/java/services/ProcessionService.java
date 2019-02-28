package services;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.Platform;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	// Managed repository ------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services -----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----------------------------------

	public Procession create() {
		Actor principal;
		Procession result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		result = new Procession();

		return result;
	}

	public Collection<Procession> findAll() {
		Collection<Procession> result;
		result = this.processionRepository.findAll();
		
		return result;
	}

	public Procession findOne(final int processionId) {
		Procession result;
		result = this.processionRepository.findOne(processionId);

		return result;
	}

	public Procession save(final Procession procession) {
		Actor principal;
		Brotherhood brotherhood;
		Procession result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		Assert.isTrue(procession.getBrotherhood().equals(principal), "not.allowed");
		
		Assert.notNull(procession);
		Assert.notNull(procession.getDescription());
		Assert.notNull(procession.getTitle());
		Assert.notNull(procession.getOrganisedMoment());
		
		brotherhood = (Brotherhood) principal;
				
		if(procession.getId() == 0){
			Assert.notNull(brotherhood.getZone());
		}		

		result = this.processionRepository.save(procession);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Procession procession) {
		Actor principal;

		Assert.notNull(procession);
		Assert.isTrue(procession.getId() != 0, "wrong.id");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		Assert.isTrue(procession.getBrotherhood().equals(principal), "not.allowed");

		this.processionRepository.delete(procession.getId());

	}

	// Other business methods -------------------------------
	
	public Procession reconstruct(Procession procession, BindingResult binding) {
		Procession result;
		Actor principal = null;
		
		if(procession.getId() == 0) {
			principal = this.actorService.findByPrincipal();
			
			result = procession;
			result.setTicker(this.utilityService.generateTicker());
			result.setPlatforms(new ArrayList<Platform>());
			result.setIsDraft(true);
			result.setBrotherhood((Brotherhood) principal);

		} else {
			result = this.findOne(procession.getId());
			
			result.setTitle(procession.getTitle());
			result.setDescription(procession.getDescription());
			result.setPlatforms(procession.getPlatforms());
			result.setIsDraft(procession.getIsDraft());
		}

		validator.validate(result, binding);
		
		return result;
	}
	
	public Collection<Procession> findProcessionsByBrotherhoodId(int brotherhoodId) {
		Collection<Procession> result;
		
		result = this.processionRepository.findProcessionsByBrotherhoodId(brotherhoodId);
		
		return result;
	}
	
	public Collection<Procession> findAcceptedProcessionsByMemberId(int memberId) {
		Collection<Procession> result;
		
		result = this.processionRepository.findAcceptedProcessionsByMemberId(memberId);
		
		return result;
	}
	
	private Collection<Procession> findProcessionsNotToApply(int memberId) {
		Collection<Procession> result;
		
		result = this.processionRepository.findProcessionsNotToApply(memberId);
		
		return result;
	}
	
	public Collection<Procession> processionsToApply(int memberId) {
		Collection<Procession> toApply;
		
		Collection<Procession> notToApply = this.findProcessionsNotToApply(memberId);

		toApply = this.findFinalProcessions();
		toApply.removeAll(notToApply);
		
		return toApply;
	}
	
	private Collection<Procession> findFinalProcessions() {
		Collection<Procession> result;
		
		result = this.processionRepository.findFinalProcessions();
		
		return result;
	}
	
	

	public Collection<Procession> findEarlyProcessions(){
		Collection<Procession>result;
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 30);
		Date maxDate = c.getTime();
		
		result = this.processionRepository.findEarlyProcessions(maxDate);
		Assert.notNull(result);
		
		return result;
		
	}
}
