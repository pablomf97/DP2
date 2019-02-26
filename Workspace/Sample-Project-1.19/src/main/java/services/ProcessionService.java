package services;


import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProcessionRepository;
import domain.Procession;

@Service
@Transactional
public class ProcessionService {

	// Managed repository ------------------------------------

	@Autowired
	private ProcessionRepository processionRepository;

	// Supporting services -----------------------------------

	// Simple CRUD methods -----------------------------------

//	public Procession create() {
//		Brotherhood principal;
//		Procession result;
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		
//		result.setPlatforms(new ArrayList<Platform>());
//		result.setDraft(true);
//
//		result = new Procession();
//
//		return result;
//	}

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

//	public Procession save(final Procession procession) {
//		Brotherhood principal;
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		Assert.isTrue(procession.getBrotherhood().equals(principal));
//		
//		Assert.notNull(procession);
//		Assert.notNull(procession.getDescription());
//		Assert.notNull(procession.getTitle());
//		Assert.notNull(procession.getOrganisedMoment());
//		
//		if(procession.getId() == 0){
//			Assert.notNull(principal.getZone());
//		}		
//
//		result = this.processionRepository.save(procession);
//		Assert.notNull(result);
//
//		return result;
//	}
//
//	public void delete(final Procession procession) {
//		Brotherhood principal;
//
//		Assert.notNull(procession);
//		Assert.isTrue(procession.getId() != 0);
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		
//		Assert.isTrue(procession.getBrotherhood().equals(principal));
//
//		this.processionRepository.delete(procession.getId());
//
//	}

	// Other business methods -------------------------------

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
