package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;

import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	
	// Managed repository ------------------------------
		@Autowired
		private SocialProfileRepository socialProfileRepository;

		// Supporting services -----------------------
		@Autowired
		private ActorService actorService;
		
		
		// Constructors
		
		public SocialProfileService() {
			super();
		}
		
		///CREATE
		public SocialProfile create(){
			SocialProfile result;
			Actor principal;

			principal=this.actorService.findByPrincipal();
			Assert.notNull(principal);

			result=new SocialProfile();
			result.setActor(principal);

			return result;
		}
		
		///FINDONE
		public SocialProfile findOne(final int socialProfileId) {
			SocialProfile result;
			Actor principal;

			principal = this.actorService.findByPrincipal();
			Assert.notNull(principal);

			result = this.socialProfileRepository.findOne(socialProfileId);
			Assert.notNull(result);

			return result;
		}
	////SAVE

		public SocialProfile save(final SocialProfile socialProfile){
			SocialProfile result;
			Actor principal;


			Assert.notNull(socialProfile);

			principal=this.actorService.findByPrincipal();
			Assert.notNull(principal);
			
			Assert.notNull(socialProfile.getName(), "socialProfile.NotEmpty");
			Assert.notNull(socialProfile.getNick(), "socialProfile.NotEmpty");
			Assert.notNull(socialProfile.getLinkProfile(), "socialProfile.NotEmpty");
			result=this.socialProfileRepository.save(socialProfile);
			Assert.notNull(result);

			return result;

		}
		//FINDALL
		public Collection<SocialProfile> findAll() {
			Collection<SocialProfile> result;

			result = this.socialProfileRepository.findAll();
			Assert.notNull(result);

			return result;
		}

}
