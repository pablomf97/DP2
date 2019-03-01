package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	// Managed repository ------------------------------
	@Autowired
	private FinderRepository finderRepository;

	// Supporting services -----------------------
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ActorService actorService;


	@Autowired
	private SystemConfigurationService systemConfigurationService;

	// Constructors
	public FinderService() {
		super();
	}
	///CREATE
	public Finder create(){
		Finder result;
		Member principal;

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal,"not.null");

		result=new Finder();
		result.setSearchResults(new ArrayList<Procession>());

		return result;
	}
	///FINDONE
	public Finder findOne(final int finderId) {
		Finder result;
		Member principal;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal,"not.null");

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result,"not.null");

		return result;
	}
	////SAVE

	public Finder save(final Finder finder){
		Finder result;
		
		Actor principal;

		principal = this.actorService.findByPrincipal();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "MEMBER"),
				"not.allowed");


		Assert.notNull(finder,"not.allowed");
		
		if (finder.getMinimumMoment()!=null && finder.getMaximumMoment()!=null){

			Assert.isTrue(finder.getMinimumMoment().before(finder.getMaximumMoment()),"not.date");
			
		}
		result=this.finderRepository.save(finder);
		Assert.notNull(result,"not.null");

		return result;

	}
	//FINDALL
	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result,"not.null");

		return result;

	}

	//DELETE
	public void delete(final Finder finder) {
		Member principal;

		Assert.notNull(finder,"not.allowed");
		Assert.isTrue(finder.getId() != 0);

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal,"not.null");


		Assert.isTrue(principal.getFinder().equals(finder),"not.allowed");

		finder.setSearchResults(null);
		finder.setArea(null);
		finder.setKeyWord(null);
		finder.setMaximumMoment(null);
		finder.setMinimumMoment(null);
		//finder.setSearchMoment(null);
		this.finderRepository.save(finder);

	}


	// Other business methods

	//expiración de la busqueda cuando termina tiempo caché
	public void deleteExpiredFinders() {
		Collection<Finder> finders;
		Date maxLivedMoment = new Date();
		int timeChachedFind;
		
		//comprobar q solo puede borrar el admin
		timeChachedFind = this.systemConfigurationService
				.findMySystemConfiguration().getTimeResultsCached();
		maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

		finders = this.findAll();

		for (final Finder finder : finders)
			if (finder.getSearchMoment().before(maxLivedMoment)) {
				this.finderRepository.delete(finder);
			}
		
	}


	public Collection<Procession> search(final Finder finder){
		Member principal;
		Date currentMoment;
		
		currentMoment=new Date(System.currentTimeMillis()-1);

		Collection<Procession> results=new ArrayList<Procession>();
		int nResults;

		Collection<Procession> resultsPagebles=new ArrayList<Procession>();

		nResults=this.systemConfigurationService.findMySystemConfiguration().getMaxResults();

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal,"not.null");

		if(finder.getMinimumMoment()!=null&& finder.getMaximumMoment()!=null){

			results=this.finderRepository.searchProcessionsDate(finder.getMinimumMoment(), finder.getMaximumMoment());
		}else if(finder.getMinimumMoment()!=null && finder.getMaximumMoment()==null){
			Date max=new Date(19249488600000L);
			results=this.finderRepository.searchProcessionsDate(finder.getMinimumMoment(), max);
		}

		else if(finder.getMinimumMoment()==null && finder.getMaximumMoment()!=null){
			Date min=new Date(126204000000L);
			results=this.finderRepository.searchProcessionsDate(min, finder.getMaximumMoment());
		}

		else if (!finder.getKeyWord().isEmpty()){
			results=this.finderRepository.searchProcessionsKeyword("%"+finder.getKeyWord()+"%");

		}else if (!finder.getArea().isEmpty()){
			results=this.finderRepository.searchProcessionsArea("%"+finder.getArea()+"%");

		}else if (finder.getMinimumMoment()==null&& finder.getMaximumMoment()==null&&finder.getKeyWord().isEmpty()&&finder.getArea().isEmpty()){
			results=this.finderRepository.searchAllProcession();
		}
		
		Assert.notNull(results,"not.null");
		for(Procession p:results){
			if(nResults>=results.size()){

				resultsPagebles.add(p);

			}

		}
		finder.setSearchResults(new ArrayList<Procession> (resultsPagebles));
		finder.setSearchMoment(currentMoment);
		this.save(finder);

		return results;

	}

	Double[] statsFinder(){
		Double [] res;
		res=this.finderRepository.StatsFinder();
		return res;
	} 

	Double ratioFinder(Finder finder){

		Double res=0.0;
		if(!finder.getArea().isEmpty()||!finder.getKeyWord().isEmpty()||finder.getMaximumMoment()!=null||finder.getMinimumMoment()!=null){
			res=1.0;
		}
		return res;



	}

}
