package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


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
		Assert.notNull(principal);

		result=new Finder();
		result.setSearchResults(new ArrayList<Procession>());

		return result;
	}
	///FINDONE
	public Finder findOne(final int finderId) {
		Finder result;
		Member principal;

		principal = this.memberService.findByPrincipal();
		Assert.notNull(principal);

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}
	////SAVE

	public Finder save(final Finder finder){
		Finder result;
		Member principal;
		Date currentMoment;


		Assert.notNull(finder);

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal);

		currentMoment=new Date(System.currentTimeMillis()-1);

		finder.setSearchMoment(currentMoment);

		if (finder.getMinimumMoment()!=null && finder.getMaximumMoment()!=null){

			Assert.isTrue(finder.getMinimumMoment().before(finder.getMaximumMoment()));

		}
		result=this.finderRepository.save(finder);
		Assert.notNull(result);

		return result;

	}
	//FINDALL
	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	//DELETE
	public void delete(final Finder finder) {
		Member principal;

		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal);


		Assert.isTrue(principal.getFinder().equals(finder));

		this.finderRepository.delete(finder);

		principal.setFinder(null);

	}


	// Other business methods

	//expiración de la busqueda cuando termina tiempo caché
	public Boolean deleteExpiredFinders() {
		Collection<Finder> finders;
		Date maxLivedMoment = new Date();
		int timeChachedFind;
		Boolean res=false;
		//comprobar q solo puede borrar el admin
		timeChachedFind = this.systemConfigurationService
				.findMySystemConfiguration().getTimeResultsCached();
		maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

		finders = this.findAll();

		for (final Finder finder : finders)
			if (finder.getSearchMoment().before(maxLivedMoment)) {
				finder.setSearchResults(new ArrayList<Procession>());
				res=true;
			}
		return res;
	}


	public Collection<Procession> search(final Finder finder){
		Member principal;
		Collection<Procession> results=new ArrayList<Procession>();
		int nResults;

		Collection<Procession> resultsPagebles=new ArrayList<Procession>();

		nResults=this.systemConfigurationService.findMySystemConfiguration().getMaxResults();

		principal=this.memberService.findByPrincipal();
		Assert.notNull(principal);

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
		Assert.notNull(results);
	for(Procession p:results){
			if(nResults>=results.size()){
				
					resultsPagebles.add(p);
				
			}
			
		}
		finder.setSearchResults(new ArrayList<Procession> (resultsPagebles));

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
