package repositories;



import java.util.Collection;
import java.util.Date;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
	

	@Query("select p from Procession p where (p.organisedMoment BETWEEN ?1 AND ?2)AND p.isDraft='0'")
	Collection<Procession> searchProcessionsDate(Date minimumMoment,Date maximumMoment);
	
	@Query("select p from Procession p where ( p.title LIKE ?1 OR p.ticker LIKE ?1 OR p.description LIKE ?1 ) AND p.isDraft='0'")
	Collection<Procession> searchProcessionsKeyword(String keyword);
	
	@Query("select p from Procession p where (p.brotherhood.zone.name LIKE ?1 AND p.isDraft='0' )")
	Collection<Procession> searchProcessionsArea(String area);
	
	@Query("select p from Procession p where  p.isDraft='0'")
	Collection<Procession> searchAllProcession();
	
	@Query("select max(m.finder.searchResults.size), min(m.finder.searchResults.size), avg(m.finder.searchResults.size),sqrt(sum(m.finder.searchResults.size* m.finder.searchResults.size) / count(m.finder.searchResults.size) -(avg(m.finder.searchResults.size) * avg(m.finder.searchResults.size))) from Member m")
	Double [] StatsFinder();
	
	
	
	
	
	
	
}