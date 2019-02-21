
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository				actorRepository;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	/**
	 * Get all actors from db
	 * 
	 * @return Collection<Actor>
	 */
	public Collection<Actor> findAll() {
		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	/**
	 * Find an actor by id in the db
	 * 
	 * @param actorId
	 * @return actor
	 */
	public Actor findOne(final int actorId) {
		final Actor result = this.actorRepository.findOne(actorId);
		return result;
	}

	/**
	 * Find the logged actor
	 * 
	 * @return actor
	 */

	public Actor findByPrincipal() {
		Actor result = null;
		final UserAccount userAccount = LoginService.getPrincipal();
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		return result;
	}

	/**
	 * Validate email pattern
	 * 
	 * @param email
	 * @return messageCode
	 */
	public String checkEmail(final String email, final String authority) {
		final String result = "";
		//		final Pattern pattern = Pattern
		//				.compile("(^(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})$)|(^((([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})>)$)");
		//		final Matcher matcher = pattern.matcher(email);
		//		if (authority.equals("ADMININISTRATOR") && matcher.matches()) {
		//			// TODO: faltar�a comprobar si se intenta insertar un admin y que
		//			// compruebe su correo para su caso
		//			// falta el pattern de admin
		//			final Pattern patternAdmin = Pattern
		//					.compile("(^(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})$)|(^((([a-z]|[0-9]){1,}[ ]{1}){1,}<(([a-z]|[0-9]){1,}[@]{1}([a-z]|[0-9]){1,}([.]{1}([a-z]|[0-9]){1,}){1,})>)$)");
		//			final Matcher matcherAdmin = patternAdmin.matcher(email);
		//			result = matcherAdmin.matches() ? "" : "actor.email.error";
		//		} else
		//			result = matcher.matches() ? "" : "actor.email.error";
		return result;
	}

	/**
	 * Validate the phone code country
	 * 
	 * @param phoneNumber
	 * @return phoneNumber
	 */
	public String checkSetPhoneCC(String phoneNumber) {
		final Pattern p = Pattern.compile("([0-9]{4}){1}([0-9]{0,})");
		final Matcher m = p.matcher(phoneNumber);
		final boolean b = m.matches();
		if (b)
			phoneNumber = this.systemConfigurationService.findAll().iterator().next().getCountryCode() + " " + phoneNumber;
		return phoneNumber;
	}

	/**
	 * Check the authority of the actor
	 * 
	 * @param actor
	 * @param authority
	 * @return boolean
	 */
	public boolean checkAuthority(final Actor actor, final String authority) {
		Assert.notNull(actor);
		boolean result = false;
		if (actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals(authority))
			result = true;
		return result;
	}

}
