package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Platform;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Integer> {
		
	@Query("select p from Platform p where p.brotherhood.id = ?1")
	Collection<Platform> findPlatformsByBrotherhoodId(int brotherhoodId);

}