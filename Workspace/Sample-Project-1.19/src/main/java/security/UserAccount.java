/*
 * UserAccount.java
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
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class UserAccount extends DomainEntity implements UserDetails {

	// Constructors -----------------------------------------------------------

<<<<<<< HEAD
	private static final long serialVersionUID = 7254823034213841482L;
=======
	private static final long	serialVersionUID	= 7254823034213841482L;

>>>>>>> Integración

	public UserAccount() {
		super();

		this.authorities = new ArrayList<Authority>();
	}

<<<<<<< HEAD
	// Attributes -------------------------------------------------------------

	private boolean isBanned;

	// UserDetails interface --------------------------------------------------

	private String username;
	private String password;
	private Collection<Authority> authorities;
=======

	// Attributes -------------------------------------------------------------

	// UserDetails interface --------------------------------------------------

	private String					username;
	private String					password;
	private Collection<Authority>	authorities;

>>>>>>> Integración

	@Size(min = 5, max = 32)
	@Column(unique = true)
	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotEmpty
	@Valid
	@ElementCollection
	@Override
	public Collection<Authority> getAuthorities() {
<<<<<<< HEAD
		// WARNING: Should return an unmodifiable copy, but it's not possible
		// with hibernate!
=======
		// WARNING: Should return an unmodifiable copy, but it's not possible with hibernate!
>>>>>>> Integración
		return this.authorities;
	}

	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(!this.authorities.contains(authority));

		this.authorities.add(authority);
	}

	public void removeAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(this.authorities.contains(authority));

		this.authorities.remove(authority);
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

<<<<<<< HEAD
	@Override
	@Transient
	public boolean isEnabled() {
		return !this.isBanned;
	}

	public boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final Boolean isBanned) {
		this.isBanned = isBanned;
=======
	@Transient
	@Override
	public boolean isEnabled() {
		return true;
>>>>>>> Integración
	}

}
