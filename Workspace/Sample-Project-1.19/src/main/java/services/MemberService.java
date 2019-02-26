
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	// Managed Repository

	@Autowired
	private MemberRepository	memberRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private Validator			validator;


	// Supporting Services

	// Simple CRUD methods

	/* Find one by ID */
	public Member findOne(final int memberId) {
		Member res;
		Assert.isTrue(memberId != 0, "Wrong member id");
		res = this.memberRepository.findOne(memberId);
		Assert.notNull(res, "The member does not exist");
		return res;

	}

	/* Find all members */
	public Collection<Member> findAll() {
		Collection<Member> res;

		res = this.memberRepository.findAll();
		Assert.notNull(res, "no.members");

		return res;
	}

	/* Create a member */
	public Member create() {
		final Member res = new Member();
		final UserAccount a = this.userAccountService.create();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		a.addAuthority(auth);
		a.setIsBanned(false);
		res.setUserAccount(a);

		return res;
	}

	public MemberForm createForm() {

		final MemberForm res = new MemberForm();

		return res;
	}

	/* Save a member */
	public Member save(final Member member) {
		Member result;
		Assert.notNull(member);
		if (member.getId() == 0) {
			final UserAccount account = member.getUserAccount();
			final Authority au = new Authority();
			au.setAuthority(Authority.MEMBER);
			Assert.isTrue(account.getAuthorities().contains(au), "You can not register with this authority");
			final UserAccount savedAccount = this.userAccountService.save(account);
			member.setUserAccount(savedAccount);
			result = this.memberRepository.save(member);
			//TODO: cuando este el sistema de box, crear los iniciales
			//TODO: crear el finder y ponerle el member nuevo
			//this.boxService.initializeDefaultBoxes(result);
			//this.finderService.initializeDefaultFinder(result);
		} else {
			final UserAccount userAccount = LoginService.getPrincipal();
			final Member memberBD = this.memberRepository.findOne(member.getId());
			Assert.isTrue(member.getUserAccount().equals(userAccount) && memberBD.getUserAccount().equals(userAccount), "This account does not belong to you");
			result = this.memberRepository.save(member);
		}
		return result;
	}

	/**
	 * Change the incomplete member to an domain object
	 * 
	 * @param member
	 * @param binding
	 * @return member
	 */
	public Member reconstruct(final MemberForm memberForm, final BindingResult binding) {
		Member result = this.create();
		if (memberForm.getId() == 0) {
			result.getUserAccount().setUsername(memberForm.getUsername());
			result.getUserAccount().setPassword(memberForm.getPassword());
			result.setAddress(memberForm.getAddress());
			result.setEmail(memberForm.getEmail());
			result.setMiddleName(memberForm.getMiddleName());
			result.setName(memberForm.getName());
			result.setPhoneNumber(memberForm.getPhoneNumber());
			result.setPhoto(memberForm.getPhoto());
			result.setSurname(memberForm.getSurname());
			this.validator.validate(memberForm, binding);

		} else {
			result = this.memberRepository.findOne(memberForm.getId());
			if (this.checkValidation(memberForm, binding, result)) {
				result.setAddress(memberForm.getAddress());
				result.setEmail(memberForm.getEmail());
				result.setMiddleName(memberForm.getMiddleName());
				result.setName(memberForm.getName());
				result.setPhoneNumber(memberForm.getPhoneNumber());
				result.setPhoto(memberForm.getPhoto());
				result.setSurname(memberForm.getSurname());
			} else {
				result = this.create();
				result.setAddress(memberForm.getAddress());
				result.setEmail(memberForm.getEmail());
				result.setMiddleName(memberForm.getMiddleName());
				result.setName(memberForm.getName());
				result.setPhoneNumber(memberForm.getPhoneNumber());
				result.setPhoto(memberForm.getPhoto());
				result.setSurname(memberForm.getSurname());
			}
		}
		return result;
	}
	private boolean checkValidation(final MemberForm memberForm, final BindingResult binding, final Member member) {
		boolean check = true;
		memberForm.setCheckBox(true);
		memberForm.setPassword(member.getUserAccount().getPassword());
		memberForm.setPassword2(member.getUserAccount().getPassword());
		memberForm.setUsername(member.getUserAccount().getUsername());
		this.validator.validate(memberForm, binding);
		if (binding.hasErrors())
			check = false;
		return check;
	}
}
