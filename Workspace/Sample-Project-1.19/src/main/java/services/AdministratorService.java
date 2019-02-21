
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private AdministratorRepository	administratorRepository;
	@Autowired
	private Validator				validator;


	/**
	 * Create a new empty admin
	 * 
	 * @return admin
	 */
	public Administrator create() {
		Assert.isTrue(this.actorService.checkAuthority(this.actorService.findByPrincipal(), "ADMINISTRATOR"));
		final Administrator res = new Administrator();
		return res;
	}

	/**
	 * Find one admin by id
	 * 
	 * @param id
	 * @return admin
	 */
	public Administrator findOne(final int id) {
		Administrator res;
		Assert.isTrue(id != 0);
		res = this.administratorRepository.findOne(id);
		Assert.notNull(res, "The brotherhood does not exist");
		return res;
	}

	/**
	 * Find all admins
	 * 
	 * @return Collection<administrator>
	 */
	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	/**
	 * Save a new admin or edit a existing admin
	 * 
	 * @param admin
	 * @return admin
	 */
	public Administrator save(final Administrator admin) {
		Administrator result;
		if (admin.getId() == 0) {
			final UserAccount account = admin.getUserAccount();
			final Authority au = new Authority();
			au.setAuthority(Authority.ADMININISTRATOR);
			Assert.isTrue(account.getAuthorities().contains(au), "You can not register with this authority");
			final UserAccount savedAccount = this.userAccountService.save(account);
			admin.setUserAccount(savedAccount);
			result = this.administratorRepository.save(admin);
			//TODO: cuando este el sistema de box, crear los iniciales
			//this.boxService.initializeDefaultBoxes(result);
		} else {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Administrator adminBD = this.administratorRepository.findOne(admin.getId());
			Assert.isTrue(admin.getUserAccount().equals(userAccount) && adminBD.getUserAccount().equals(userAccount), "This account does not belong to you");
			result = this.administratorRepository.save(admin);
		}
		return result;
	}

	/**
	 * Remove the admin
	 * 
	 * @param id
	 */
	public void delete(final int id) {
		final Administrator brotherhood = this.administratorRepository.findOne(id);
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(brotherhood.getUserAccount().equals(userAccount), "This account does not belong to you");
		this.administratorRepository.delete(id);
	}

	/**
	 * Change the incomplete administrator to an domain object
	 * 
	 * @param administrator
	 * @param binding
	 * @return administrator
	 */
	public Administrator reconstruct(final Administrator administrator, final BindingResult binding) {
		Administrator result;
		if (administrator.getId() == 0) {
			final UserAccount a = this.userAccountService.create();
			final Authority auth = new Authority();
			auth.setAuthority(Authority.ADMININISTRATOR);
			a.addAuthority(auth);
			a.setIsBanned(false);
			a.setUsername(administrator.getUserAccount().getUsername());
			a.setPassword(administrator.getUserAccount().getPassword());
			administrator.setUserAccount(a);
			result = administrator;
		} else {
			result = this.administratorRepository.findOne(administrator.getId());
			result.setAddress(administrator.getAddress());
			result.setEmail(administrator.getEmail());
			result.setMiddleName(administrator.getMiddleName());
			result.setName(administrator.getName());
			result.setPhoneNumber(administrator.getPhoneNumber());
			result.setPhoto(administrator.getPhoto());
			result.setSurname(administrator.getSurname());
		}
		this.validator.validate(result, binding);

		return result;
	}
}
