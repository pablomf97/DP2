package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PlatformRepository;
import domain.Platform;

@Service
@Transactional
public class PlatformService {

	// Managed repository ------------------------------------

	@Autowired
	private PlatformRepository platformRepository;

	// Supporting services -----------------------------------

	// Simple CRUD methods -----------------------------------

//	public Platform create() {
//		Brotherhood principal;
//		Platform result;
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		
//		result.setEstablismentDate((new Date(System.currentTimeMillis() - 1)));
//
//		result = new Platform();
//
//		return result;
//	}

	public Collection<Platform> findAll() {
		Collection<Platform> result;
		result = this.platformRepository.findAll();
		
		return result;
	}

	public Platform findOne(final int platformId) {
		Platform result;
		result = this.platformRepository.findOne(platformId);

		return result;
	}

//	public Platform save(final Platform platform) {
//		Brotherhood principal;
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		Assert.isTrue(platform.getBrotherhood().equals(principal));
//		
//		Assert.notNull(platform);
//		Assert.notNull(platform.getDescription());
//		Assert.notNull(platform.getTitle());
//		
//		if(platform.getId() == 0){
//			Assert.notNull(principal.getZone());
//		}		
//
//		result = this.platformRepository.save(platform);
//		Assert.notNull(result);
//
//		return result;
//	}
//
//	public void delete(final Platform platform) {
//		Brotherhood principal;
//
//		Assert.notNull(platform);
//		Assert.isTrue(platform.getId() != 0);
//
//		principal = this.brotherhoodService.findByPrincipal();
//		Assert.notNull(principal);
//		
//		Assert.isTrue(platform.getBrotherhood().equals(principal));
//
//		this.platformRepository.delete(platform.getId());
//
//	}

	// Other business methods -------------------------------

}
