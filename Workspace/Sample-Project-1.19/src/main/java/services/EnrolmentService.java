//package services;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import repositories.EnrolmentRepository;
//import domain.Actor;
//import domain.Brotherhood;
//import domain.Enrolment;
//import domain.Member;
//
//@Service
//@Transactional
//public class EnrolmentService {
//
//	// Managed repository
//
//	@Autowired
//	private EnrolmentRepository enrolmentRepository;
//
//	// Supporting Services
//
//	@Autowired
//	private MemberService memberService;
//
//	@Autowired
//	private BrotherhoodService brotherhoodService;
//
//	@Autowired
//	private ActorService actorService;
//
//	// Simple CRUD methods
//
//	/* Find one by ID */
//	public Enrolment findOne(final int enrolmentId) {
//		Enrolment res;
//
//		res = this.enrolmentRepository.findOne(enrolmentId);
//
//		return res;
//
//	}
//
//	/* Find all enrollments */
//	public Collection<Enrolment> findAll() {
//		Collection<Enrolment> res;
//
//		res = this.enrolmentRepository.findAll();
//		Assert.notNull(res, "no.enrolments");
//
//		return res;
//	}
//
//	/* Create an enrollment */
//	public Enrolment create() {
//		Member principal;
//		Enrolment res;
//
//		principal = this.memberService.findByPrincipal();
//		Assert.notNull(principal, "not.allowed");
//
//		res = new Enrolment();
//
//		List<Date> dropOutMoment = new ArrayList<>();
//		res.setDropOutMoment(dropOutMoment);
//
//		return res;
//	}
//
//	/* Save an enrollment */
//	public Enrolment save(Enrolment enrolment) {
//		Actor principal;
//		Enrolment res;
//
//		principal = this.actorService.findByPrincipal();
//		Assert.isTrue(principal instanceof Member
//				|| principal instanceof Brotherhood, "not.allowed");
//
//		Assert.notNull(enrolment, "null.enrolment");
//
//		res = this.enrolmentRepository.save(enrolment);
//
//		return res;
//	}
//}
