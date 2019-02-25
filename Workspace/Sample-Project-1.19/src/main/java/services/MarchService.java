package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MarchRepository;
import domain.Actor;
import domain.Brotherhood;
import domain.March;
import domain.Member;

@Service
@Transactional
public class MarchService {

	// Managed repository ------------------------------------

	@Autowired
	private MarchRepository marchRepository;

	// Supporting services -----------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----------------------------------

	public March create() {
		Actor principal;
		March result;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "MEMBER"), "not.allowed");

		result = new March();

		return result;
	}

	public Collection<March> findAll() {
		Collection<March> result;
		result = this.marchRepository.findAll();
		
		return result;
	}

	public March findOne(final int marchId) {
		March result;
		result = this.marchRepository.findOne(marchId);

		return result;
	}

	public March save(final March march) {
		Member member;
		Brotherhood brotherhood;
		Actor principal;
		March result;

		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal, "not.allowed");
		Assert.notNull(march);

		if(this.actorService.checkAuthority(principal, "MEMBER")){
			
			Assert.isTrue(march.getId()== 0, "wrong.id");
			
			member = (Member) principal;
			march.setStatus("PENDING");
			march.setRow(null);
			march.setCol(null);
			march.setMember(member);
			
		} else if (this.actorService.checkAuthority(principal, "BROTHERHOOD")){
			
			Assert.isTrue(march.getId() != 0);
			
			brotherhood = (Brotherhood) principal;
			
			Assert.isTrue(march.getProcession().getBrotherhood().equals(brotherhood), "not.allowed");
			if(march.getStatus() == "REJECT") {
				Assert.notNull(march.getReason());
			}
			
		}

		result = this.marchRepository.save(march);
		Assert.notNull(result);

		return result;
	}

	public void delete(final March march) {
		Actor principal;

		Assert.notNull(march);
		Assert.isTrue(march.getId() != 0, "wrong.id");

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "MEMBER"), "not.allowed");
		
		Assert.isTrue(march.getMember().equals(principal), "not.allowed");

		this.marchRepository.delete(march.getId());

	}

	// Other business methods -------------------------------
	
	public March reconstruct(March march, BindingResult binding) {
		March result;
		
		if(march.getId() == 0) {
			result = march;
		} else {
			result = this.findOne(march.getId());
			
			result.setStatus(march.getStatus());
			result.setRow(march.getRow());
			result.setCol(march.getCol());
			result.setReason(march.getReason());
			
		}
		
		validator.validate(result, binding);
		
		return result;
	}
	
	public Collection<March> findMarchsByMemberId(int memberId) {
		Collection<March> result;
		
		result = this.marchRepository.findMarchsByMemberId(memberId);
		
		return result;
	}

}
