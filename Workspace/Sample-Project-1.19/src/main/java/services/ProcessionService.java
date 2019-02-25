package services;

import java.util.ArrayList;
import java.util.Collection;

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
	private Validator validator;

	// Simple CRUD methods -----------------------------------

	public Procession create() {
		Actor principal;
		Procession result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "BROTHERHOOD"), "not.allowed");
		
		result = new Procession();
		
		result.setPlatforms(new ArrayList<Platform>());
		result.setIsDraft(true);

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
	
	public Collection<Procession> findProcessionsByMemberId(int memberId) {
		Collection<Procession> result;
		
		result = this.processionRepository.findProcessionsByMemberId(memberId);
		
		return result;
	}

}
