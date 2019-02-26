package repositories;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {
		
	@Query("select p from Procession p where p.organisedMoment > NOW() AND p.organisedMoment < ?1")
	Collection<Procession> findEarlyProcessions(Date maxDate);

}
