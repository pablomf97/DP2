//package services;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import repositories.PositionRepository;
//import domain.Administrator;
//import domain.Position;
//
//@Service
//@Transactional
//public class PositionService {
//
//	// Managed Repository
//
//	@Autowired
//	private PositionRepository positionRepository;
//
//	// Supporting Services
//
//	@Autowired
//	private AdministratorService administratorService;
//
//	// Simple CRUD methods
//
//	/* Find one by ID */
//	public Position findOne(final int positionId) {
//		Position res;
//
//		res = this.positionRepository.findOne(positionId);
//
//		return res;
//
//	}
//
//	/* Find all members */
//	public Collection<Position> findAll() {
//		Collection<Position> res;
//
//		res = this.positionRepository.findAll();
//		Assert.notNull(res, "no.members");
//
//		return res;
//	}
//
//	/* Create a position */
//	public Position create() {
//		Administrator principal;
//		Position res;
//
//		principal = this.administratorService.findByPrincipal();
//		Assert.notNull(principal, "not.allowed");
//
//		res = new Position();
//		Map<String, String> name = new HashMap<>();
//
//		res.setName(name);
//
//		return res;
//	}
//
//	/* Save a position */
//	public Position save(Position position) {
//		Administrator principal;
//		Position res;
//
//		principal = this.administratorService.findByPrincipal();
//		Assert.notNull(principal, "not.allowed");
//
//		Assert.notNull(position, "null.position");
//
//		res = this.positionRepository.save(position);
//	}
//
//	/* Delete a position */
//	public void delete(Position position) {
//		Administrator principal;
//
//		principal = this.administratorService.findByPrincipal();
//		Assert.notNull(principal, "not.allowed");
//
//		Assert.notNull(position, "null.position");
//
//		this.positionRepository.delete(position);
//	}
//}
