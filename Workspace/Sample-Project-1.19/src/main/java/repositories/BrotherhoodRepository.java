package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {
		
	@Query("select b from Brotherhood b where b.zone.id = ?1")
	Collection<Brotherhood> brotherhoodsByZone(int zoneId);
	
	
}
