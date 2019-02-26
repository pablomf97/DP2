package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MarchRepository;
import domain.March;

@Service
@Transactional
public class MarchService {

	// Managed repository ------------------------------------

	@Autowired
	private MarchRepository marchRepository;

	// Supporting services -----------------------------------

	// Simple CRUD methods -----------------------------------

	public March create() {
		//Member principal;
		March result;

//		principal = this.memberService.findByPrincipal();
//		Assert.notNull(principal);

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

//	public March save(final March march) {
//		Member member;
//		Brotherhood brotherhood;
//		Actor actor;
//
//		principal = this.actorService.findByPrincipal();
//		Assert.notNull(principal);
//		Assert.notNull(march);
//		
//		if(actor instanceof Member){
//			
//			Assert.isTrue(march.getId()== 0);
//			
//			member = this.memberService.findByPrincipal();
//			
//			march.setStatus("PENDING");
//			march.setRow(null);
//			march.setCol(null);
//			march.setMember(member);
//			
//		} else if (actor instanceof Brotherhood){
//			
//			Assert.isTrue(march.getId() != 0);
//			
//			brotherhood = this.brotherhoodService.findByPrincipal();
//			
//			Assert.isTrue(march.getProcession().getBrotherhood().equals(brotherhood));
//			if(march.getStatus() == "REJECT") {
//				Assert.notNull(march.getReason());
//			}
//			
//		}
//
//		result = this.marchRepository.save(march);
//		Assert.notNull(result);
//
//		return result;
//	}
//
//	public void delete(final March march) {
//		Member principal;
//
//		Assert.notNull(march);
//		Assert.isTrue(march.getId() != 0);
//
////		principal = this.marchService.findByPrincipal();
////		Assert.notNull(principal);
//		
//		Assert.isTrue(march.getMember().equals(principal));
//
//		this.marchRepository.delete(march.getId());
//
//	}

	// Other business methods -------------------------------

	public Double ratioApprovedRequests(){
		Double result;
		
		result = this.marchRepository.ratioApprovedRequests();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double ratioPendingRequests(){
		Double result;
		
		result = this.marchRepository.ratioPendingRequests();
		Assert.notNull(result);
		
		return result;
	}
	
	public Double ratioRejectedRequests(){
		Double result;
		
		result = this.marchRepository.ratioRejectedRequests();
		Assert.notNull(result);
		
		return result;
	}
	
	public Collection<March> findByMember(int memberId){
		Collection<March> result;
		
		result = this.marchRepository.findByMember(memberId);
		Assert.notNull(result);
		
		return result;
	}
}
