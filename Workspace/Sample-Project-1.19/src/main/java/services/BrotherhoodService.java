
package services;

import java.util.ArrayList;
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
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;
	@Autowired
	private Validator				validator;


	/**
	 * Create a new empty brotherhood
	 * 
	 * @return brotherhood
	 */
	public Brotherhood create() {

		final Brotherhood res = new Brotherhood();

		return res;
	}
	public BrotherhoodForm createForm() {

		final BrotherhoodForm res = new BrotherhoodForm();

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
	public Brotherhood reconstruct(final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		Brotherhood result = this.create();
		if (brotherhoodForm.getId() == 0) {
			final UserAccount a = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.BROTHERHOOD);
			a.addAuthority(auth);
			a.setIsBanned(false);
			a.setUsername(brotherhoodForm.getUsername());
			a.setPassword(brotherhoodForm.getPassword());
			result.setUserAccount(a);
			if (brotherhoodForm.getPictures() == null)
				result.setPictures("");
			final Date establishmentDate = new Date();
			result.setEstablishmentDate(establishmentDate);
			result.setAddress(brotherhoodForm.getAddress());
			result.setEmail(brotherhoodForm.getEmail());
			result.setMiddleName(brotherhoodForm.getMiddleName());
			result.setName(brotherhoodForm.getName());
			result.setPhoneNumber(brotherhoodForm.getPhoneNumber());
			result.setPhoto(brotherhoodForm.getPhoto());
			result.setPictures("");//TODO: ver como meter todas las imágenes
			result.setSurname(brotherhoodForm.getSurname());
			result.setTitle(brotherhoodForm.getTitle());
		} else {
			result = this.brotherhoodRepository.findOne(brotherhoodForm.getId());
			result.setAddress(brotherhoodForm.getAddress());
			result.setEmail(brotherhoodForm.getEmail());
			result.setMiddleName(brotherhoodForm.getMiddleName());
			result.setName(brotherhoodForm.getName());
			result.setPhoneNumber(brotherhoodForm.getPhoneNumber());
			result.setPhoto(brotherhoodForm.getPhoto());
			result.setPictures("");//TODO: ver como meter todas las imágenes
			result.setSurname(brotherhoodForm.getSurname());
			result.setTitle(brotherhoodForm.getTitle());
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<String> getSplitPictures(final String pictures) {
		final Collection<String> res = new ArrayList<>();
		final String[] slice = pictures.split("<>");
		for (final String p : slice)
			res.add(p);
		return res;
	}
}
