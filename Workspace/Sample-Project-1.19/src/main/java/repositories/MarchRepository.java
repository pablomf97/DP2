package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.March;

@Repository
public interface MarchRepository extends JpaRepository<March, Integer> {
		


}
