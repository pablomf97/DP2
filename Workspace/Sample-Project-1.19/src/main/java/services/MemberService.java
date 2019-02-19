package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Finder;
import domain.Member;

@Service
@Transactional
public class MemberService {

	// Managed Repository

	@Autowired
	private MemberRepository memberRepository;

	// Supporting Services

	// Simple CRUD methods

	/* Find one by ID */
	public Member findOne(final int memberId) {
		Member res;

		res = this.memberRepository.findOne(memberId);

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
		Member res;
		res = new Member();

		Authority authority = new Authority();
		authority.setAuthority(Authority.MEMBER);

		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);

		Finder finder = new Finder();
		res.setFinder(finder);

		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);
		userAccount.setIsBanned(false);
		res.setUserAccount(userAccount);

		return res;
	}

	/* Save a member */
	public Member save(final Member member) {
		Member result;
		Actor principal;

		Assert.notNull(member);

		// Assert.isTrue(this.utilityService.validEmail(admin.getEmail()),
		// "administrator.email");

		principal = this.findByPrincipal();
		Assert.notNull(principal);
		if (member.getId() == 0) {
			Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			member.getUserAccount().setPassword(
					passwordEncoder.encodePassword(member.getUserAccount()
							.getPassword(), null));
		}

		result = this.memberRepository.save(member);

		return result;
	}

	// Other business methods

	public Member findByPrincipal() {
		Member res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		res = this.findMemberByUserAccount(userAccount.getId());
		Assert.notNull(res);

		return res;
	}

	public Member findMemberByUserAccount(final int userAccountId) {
		Assert.isTrue(userAccountId != 0);

		Member res;

		res = this.memberRepository.findMemberByUserAccount(userAccountId);

		Assert.notNull(res);

		return res;
	}
}
