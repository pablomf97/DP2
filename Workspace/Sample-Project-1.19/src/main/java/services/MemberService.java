package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Brotherhood;
import domain.Finder;
import domain.March;
import domain.Member;

@Service
@Transactional
public class MemberService {

	// Managed Repository

	@Autowired
	private MemberRepository memberRepository;

	// Supporting Services

	@Autowired

	private Validator validator;

	private BrotherhoodService brotherhoodService;

	@Autowired
	private EnrolmentService enrolmentService;

	@Autowired
	private AdministratorService AdministratorService;
	
	@Autowired
	private MarchService marchService;


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


	// Reconstruct
	public Member reconstruct(Member member, BindingResult binding) {
		Member res;

		if (member.getId() == 0)
			res = member;
		else {
			res = this.memberRepository.findOne(member.getId());

			res.setName(member.getName());
			res.setMiddleName(member.getMiddleName());
			res.setSurname(member.getSurname());
			res.setPhoneNumber(member.getPhoneNumber());
			res.setAddress(member.getAddress());
			res.setPhoto(member.getPhoto());

		}
		this.validator.validate(res, binding);

		return res;
	}


	public Collection<Member> findAllMembersByBrotherhood(int brotherhoodId){
		Collection<Member> result;

		result = this.memberRepository.findAllMembersByBrotherhood(brotherhoodId);
		Assert.notNull(result);

		return result;

	}


	//Dashboard --------------------------------------------------------------------

	public Double averageMemberPerBrotherhood(){

		Collection<Brotherhood> brotherhoods;
		Collection<Member> members;
		int total = 0;
		Double result;

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notEmpty(brotherhoods);

		for(Brotherhood b: brotherhoods){
			members = this.findAllMembersByBrotherhood(b.getId());
			total = total + members.size();
		}

		result = (double) (total / brotherhoods.size());

		return result;
	}

	public Double minMemberPerBrotherhood(){
		Collection<Brotherhood> brotherhoods;
		Collection<Member> members;
		int count = 0;
		Double result = 0.0;

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notEmpty(brotherhoods);

		for(Brotherhood b: brotherhoods){
			members = this.findAllMembersByBrotherhood(b.getId());

			if(count == 0){
				result = (double) members.size();
			}

			if(members.size()< result){
				result = (double) members.size();

			}
			count++;
		}
		return result;
	}
	
	public Double maxMemberPerBrotherhood(){
		Collection<Brotherhood> brotherhoods;
		Collection<Member> members;
		int count = 0;
		Double result = 0.0;

		brotherhoods = this.brotherhoodService.findAll();
		Assert.notEmpty(brotherhoods);

		for(Brotherhood b: brotherhoods){
			members = this.findAllMembersByBrotherhood(b.getId());

			if(count == 0){
				result = (double) members.size();
			}

			if(members.size()> result){
				result = (double) members.size();

			}
			count++;
		}
		return result;
	}
	
	public Collection<Member> acceptedMembers(){
		Collection<Member> members;
		Collection<March> marchsByMember,marchs;
		int totalAccepted = 0;
		Double percent;
		
		marchs = this.marchService.findAll();
		Assert.notNull(marchs);
		
		members = this.findAll();
		Assert.notNull(members);
		
		
		
		percent = totalAccepted*0.1;
		
		for(Member m: members){
			marchsByMember = this.marchService.findByMember(m.getId());
			
			for(March ma: marchsByMember){
				if(ma.getStatus().equals("APPROVED")){
					totalAccepted++;
				}
			}
			
			
			
			
		}
		return members;
		
	}


}
