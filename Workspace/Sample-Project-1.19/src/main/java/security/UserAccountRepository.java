/*
 * UserAccountRepository.java
 * 
<<<<<<< HEAD
 * Copyright (C) 2018 Universidad de Sevilla
=======
 * Copyright (C) 2019 Universidad de Sevilla
>>>>>>> Integración
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findByUsername(String username);

}
