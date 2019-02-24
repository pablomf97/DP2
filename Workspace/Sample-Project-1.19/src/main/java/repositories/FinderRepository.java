package repositories;


import java.awt.print.Pageable;
import java.util.Collection;
import java.util.Date;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
		
	//select p from Procession p where (p.organisedMoment BETWEEN '2017/08/08 12:12' AND '2030/08/08 12:12') AND (p.title LIKE '%%' OR p.ticker LIKE '%%' OR p.description LIKE '%%' )AND (p.brotherhood.zone.name LIKE '%%' );
	
	//@Query("select p from Procession p where (p.organisedMoment BETWEEN ?1 AND ?2) OR (p.title LIKE ?3 AND p.ticker LIKE ?3 AND p.description LIKE ?3 )AND (p.brotherhood.zone.name LIKE ?4 )")
	//Collection<Procession> searchProcessions(Date minimumMoment,Date maximumMoment,String keyword,String area);
	@Query("select p from Procession p where (p.organisedMoment BETWEEN ?1 AND ?2)")
	Collection<Procession> searchProcessionsDate(Date minimumMoment,Date maximumMoment);
	
	@Query("select p from Procession p where (p.title LIKE ?1 OR p.ticker LIKE ?1 OR p.description LIKE ?1 )")
	Collection<Procession> searchProcessionsKeyword(String keyword);
	
	@Query("select p from Procession p where (p.brotherhood.zone.name LIKE ?1 )")
	Collection<Procession> searchProcessionsArea(String area);
	
	
}