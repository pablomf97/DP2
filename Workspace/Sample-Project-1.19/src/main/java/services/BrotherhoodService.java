
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolment;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;
	@Autowired
	private Validator				validator;

	@Autowired
	private EnrolmentService enrolmentService;

	/**
	 * Create a new empty brotherhood
	 * 
	 * @return brotherhood
	 */
	public Brotherhood create() {

		final Brotherhood res = new Brotherhood();
		final UserAccount a = this.userAccountService.create();

		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		a.addAuthority(auth);
		res.setUserAccount(a);
		/*
		 * res.setBan(false);
		 * res.setSpammer(false);
		 * res.setScore(0.0);
		 */
		final Date establishmentDate = new Date();
		res.setEstablishmentDate(establishmentDate);

		return res;
	}

	/**
	 * Find one brotherhood by id
	 * 
	 * @param id
	 * @return brotherhood
	 */
	public Brotherhood findOne(final int id) {
		Brotherhood res;
		Assert.isTrue(id != 0);
		res = this.brotherhoodRepository.findOne(id);
		Assert.notNull(res, "The brotherhood does not exist");
		return res;
	}

	/**
	 * Find all brotherhoods
	 * 
	 * @return Collection<brotherhood>
	 */
	public Collection<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	/**
	 * Save a new brotherhood or edit a existing brotherhood
	 * 
	 * @param brotherhood
	 * @return brotherhood
	 */
	public Brotherhood save(final Brotherhood brotherhood) {
		Brotherhood result;
		if (brotherhood.getId() == 0) {
			final UserAccount account = brotherhood.getUserAccount();
			final Authority au = new Authority();
			au.setAuthority(Authority.BROTHERHOOD);
			Assert.isTrue(account.getAuthorities().contains(au), "You can not register with this authority");
			final UserAccount savedAccount = this.userAccountService.save(account);
			brotherhood.setUserAccount(savedAccount);
			//TODO: esta parte de valores por defecto, quizas se tenga que borrar, pero por ahora lo ponemos
			//por si acaso, ya que con los nuevos forms no haga falta
			/*
			 * brotherhood.setBan(false);
			 * brotherhood.setSpammer(false);
			 * brotherhood.setScore(0.0);
			 */
			final Date establishmentDate = new Date();
			brotherhood.setEstablishmentDate(establishmentDate);
			//Hasta aquí se borraría
			result = this.brotherhoodRepository.save(brotherhood);
			//TODO: cuando este el sistema de box, crear los iniciales
			//this.boxService.initializeDefaultBoxes(result);
		} else {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Brotherhood brotherhoodBD = this.brotherhoodRepository.findOne(brotherhood.getId());
			Assert.isTrue(brotherhood.getUserAccount().equals(userAccount) && brotherhoodBD.getUserAccount().equals(userAccount), "This account does not belong to you");
			result = this.brotherhoodRepository.save(brotherhood);
		}
		return result;
	}

	/**
	 * Remove the brotherhood
	 * 
	 * @param id
	 */
	public void delete(final int id) {
		final Brotherhood brotherhood = this.brotherhoodRepository.findOne(id);
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(brotherhood.getUserAccount().equals(userAccount), "This account does not belong to you");
		this.brotherhoodRepository.delete(id);
	}

	/**
	 * Change the incomplete brotherhood to an domain object
	 * 
	 * @param brotherhood
	 * @param binding
	 * @return brotherhood
	 */
	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood result;

		if (brotherhood.getId() == 0)
			/*
			 * brotherhood.setBan(false);
			 * final Date establishmentDate = new Date();
			 * brotherhood.setEstablishmentDate(establishmentDate);
			 * brotherhood.setScore(0.0);
			 * brotherhood.setSpammer(false);
			 */
			result = brotherhood;
		else {
			result = this.brotherhoodRepository.findOne(brotherhood.getId());
			result.setAddress(brotherhood.getAddress());
			result.setEmail(brotherhood.getEmail());
			result.setMiddleName(brotherhood.getMiddleName());
			result.setName(brotherhood.getName());
			result.setPhoneNumber(brotherhood.getPhoneNumber());
			result.setPhoto(brotherhood.getPhoto());
			result.setPictures(brotherhood.getPictures());
			result.setSurname(brotherhood.getSurname());
			result.setTitle(brotherhood.getTitle());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Brotherhood largestBrotherhood(){
		Brotherhood result = null;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int count = 0;

		brotherhoods = this.findAll();
		Assert.notEmpty(brotherhoods);

		for(Brotherhood b: brotherhoods){
			enrolments = this.enrolmentService.findActiveEnrolmentByBrotherhood(b.getId());

			if(count == 0){
				result = b;
			}

			if(this.enrolmentService.getEnrollmentsByBrotherhood(result.getId()).size() < enrolments.size()){
				result = b;
			}

			count++;
		}

		return result;
	}

	
	public Brotherhood smallestBrotherhood(){
		Brotherhood result = null;
		Collection<Brotherhood> brotherhoods;
		Collection<Enrolment> enrolments;
		int count = 0;

		brotherhoods = this.findAll();
		Assert.notEmpty(brotherhoods);

		for(Brotherhood b: brotherhoods){
			enrolments = this.enrolmentService.findActiveEnrolmentByBrotherhood(b.getId());

			if(count == 0){
				result = b;
			}

			if(this.enrolmentService.getEnrollmentsByBrotherhood(result.getId()).size() > enrolments.size()){
				result = b;
			}

			count++;
		}

		return result;
	}

}
