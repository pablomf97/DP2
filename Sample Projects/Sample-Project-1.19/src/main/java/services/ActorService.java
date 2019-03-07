
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public Actor create() {

		final Actor res = new Actor();
		final UserAccount a = new UserAccount();

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		a.addAuthority(auth);
		res.setUserAccount(a);

		return res;
	}

	public ActorForm createForm() {
		final ActorForm res = new ActorForm();
		return res;
	}

	public Actor findOne(final int id) {
		Actor res;
		Assert.isTrue(id != 0);
		res = this.actorRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor save(final Actor actor) {
		Actor result;
		if (actor.getId() == 0) {
			final UserAccount account = actor.getUserAccount();
			final Authority au = new Authority();
			au.setAuthority(Authority.CUSTOMER);
			Assert.isTrue(account.getAuthorities().contains(au));
			final UserAccount savedAccount = this.userAccountRepository.save(account);
			actor.setUserAccount(savedAccount);
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(actor.getUserAccount().getPassword(), null);
			actor.getUserAccount().setPassword(hash);
			result = this.actorRepository.save(actor);
		} else {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Actor adminBD = this.actorRepository.findOne(actor.getId());
			Assert.isTrue(actor.getUserAccount().equals(userAccount) && adminBD.getUserAccount().equals(userAccount), "This account does not belong to you");
			result = this.actorRepository.save(actor);
		}
		return result;
	}

	public Actor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Actor result = this.create();
		if (actorForm.getId() == 0) {
			result.getUserAccount().setUsername(actorForm.getUsername());
			result.getUserAccount().setPassword(actorForm.getPassword());
			result.setEmail(actorForm.getEmail());
			result.setMiddleName(actorForm.getMiddleName());
			result.setName(actorForm.getName());
			result.setSurname(actorForm.getSurname());
			this.validator.validate(actorForm, binding);

		} else {
			result = this.actorRepository.findOne(actorForm.getId());
			Assert.notNull(result);
			if (this.checkValidation(actorForm, binding, result)) {
				result.setEmail(actorForm.getEmail());
				result.setMiddleName(actorForm.getMiddleName());
				result.setName(actorForm.getName());
				result.setSurname(actorForm.getSurname());
			} else {
				result = this.create();
				result.setEmail(actorForm.getEmail());
				result.setMiddleName(actorForm.getMiddleName());
				result.setName(actorForm.getName());
				result.setSurname(actorForm.getSurname());
				result.setId(actorForm.getId());
			}
		}
		return result;
	}

	private boolean checkValidation(final ActorForm actorForm, final BindingResult binding, final Actor actor) {
		boolean check = true;
		actorForm.setCheckBox(true);
		actorForm.setPassword(actor.getUserAccount().getPassword());
		actorForm.setPassword2(actor.getUserAccount().getPassword());
		actorForm.setUsername(actor.getUserAccount().getUsername());
		this.validator.validate(actorForm, binding);
		if (binding.hasErrors())
			check = false;
		return check;
	}

	public Actor findByPrincipal() {
		Actor result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	public String checkPass(final String password1, final String password2) {
		String result = "";
		if (!password1.equals(password2))
			result = "actor.check.passW";
		return result;
	}

	public String checkUniqueUser(final String username) {
		String result = "";
		if (this.userAccountRepository.findByUsername(username) != null)
			result = "actor.check.unique.user";
		return result;
	}
}
