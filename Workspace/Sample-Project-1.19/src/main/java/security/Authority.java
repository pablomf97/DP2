/*
 * Authority.java
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

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
public class Authority implements GrantedAuthority {

	// Constructors -----------------------------------------------------------

<<<<<<< HEAD
	private static final long serialVersionUID = 1L;
=======
	private static final long	serialVersionUID	= 1L;

>>>>>>> Integración

	public Authority() {
		super();
	}

<<<<<<< HEAD
	// Values -----------------------------------------------------------------

	public static final String ADMININISTRATOR = "ADMINISTRATOR";
	public static final String MEMBER = "MEMBER";
	public static final String BROTHERHOOD = "BROTHERHOOD";

	// Attributes -------------------------------------------------------------

	private String authority;

	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMININISTRATOR + "|" + Authority.MEMBER
			+ "|" + Authority.BROTHERHOOD + "$")
=======

	// Values -----------------------------------------------------------------

	public static final String	ADMIN		= "ADMIN";
	public static final String	CUSTOMER	= "CUSTOMER";

	// Attributes -------------------------------------------------------------

	private String				authority;


	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMIN + "|" + Authority.CUSTOMER + "$")
>>>>>>> Integración
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
<<<<<<< HEAD
		authority.setAuthority(Authority.ADMININISTRATOR);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.MEMBER);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
=======
		authority.setAuthority(Authority.ADMIN);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
>>>>>>> Integración
		result.add(authority);

		return result;
	}

	// Object interface -------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
<<<<<<< HEAD
			result = (this.getAuthority().equals(((Authority) other)
					.getAuthority()));
=======
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));
>>>>>>> Integración

		return result;
	}

	@Override
	public String toString() {
		return this.authority;
	}

}
