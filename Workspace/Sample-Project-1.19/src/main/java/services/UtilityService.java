package services;

import java.security.SecureRandom;
import java.util.Calendar;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Procession;

@Service
@Transactional
public class UtilityService {

	// Supporting Services ------------------------------------

	@Autowired
	private ProcessionService processionService;


	// Utility methods ----------------------------------------

	public String generateTicker() {
		String uniqueTicker = null;
		Calendar date;
		String year, month, day, alphaNum, todayDate;
		boolean unique = false;

		date = Calendar.getInstance();
		date.setTime(LocalDate.now().toDate());
		year = String.valueOf(date.get(Calendar.YEAR));
		year = year.substring(year.length() - 2, year.length());
		month = String.valueOf(date.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			day = "0" + day;
		}

		while (unique == false) {
			alphaNum = this.randomString();
			todayDate = year + month + day;
			uniqueTicker = todayDate + "-" + alphaNum;
			for (final Procession procession : this.processionService.findAll())
				if (procession.getTicker().equals(uniqueTicker))
					continue;
			unique = true;
		}
		return uniqueTicker;
	}

	public String randomString() {

		final String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final SecureRandom rnd = new SecureRandom();
		final int length = 5;

		final StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++)
			stringBuilder.append(possibleChars.charAt(rnd.nextInt(possibleChars
					.length())));
		return stringBuilder.toString();

	}

//	public List<String> getCreditCardMakes() {
//
//		final String makes = this.systemConfigurationService
//				.findMySystemConfiguration().getListCreditCardMakes();
//		final List<String> listCCMakes = new ArrayList<String>(
//				Arrays.asList(makes.split(",")));
//		return listCCMakes;
//	}
//
//	public List<String> getNegativeWords() {
//		Administrator principal;
//
//		principal = this.administratorService.findByPrincipal();
//		Assert.notNull(principal);
//
//		final String makes = this.systemConfigurationService
//				.findMySystemConfiguration().getNegativeWords();
//		final List<String> listNegWords = new ArrayList<String>(
//				Arrays.asList(makes.split(",")));
//		return listNegWords;
//	}
//
//	public int getNumberNegativeWords(final String s) {
//		int res = 0;
//		final List<String> negativeWords = this.getNegativeWords();
//		final String[] words = s.split("(¿¡,.-_/!?) ");
//		for (final String a : words)
//			if (negativeWords.contains(a))
//				res++;
//		return res;
//	}
//
//	public List<String> getPositiveWords() {
//		Administrator principal;
//
//		principal = this.administratorService.findByPrincipal();
//		Assert.notNull(principal);
//
//		final String makes = this.systemConfigurationService
//				.findMySystemConfiguration().getPositiveWords();
//		final List<String> listPosWords = new ArrayList<String>(
//				Arrays.asList(makes.split(",")));
//		return listPosWords;
//	}
//
//	public int getNumberPositiveWords(final String s) {
//		int res = 0;
//		final List<String> positiveWords = this.getPositiveWords();
//		final String[] words = s.split("(¿¡,.-_/!?) ");
//		for (final String a : words)
//			if (positiveWords.contains(a))
//				res++;
//		return res;
//	}
//
//	public boolean validEmail(String email) {
//		
//		String toValidate = email.replace(" ","");
//		Pattern pattern = Pattern.compile("([0-9a-z ]+((<)|())+([0-9a-z])+@+(()|([0-9a-z.]))+((>)|())|((<)|())+([0-9a-z])+@+(()|([0-9a-z.]))+((>)|()))");
//		Matcher match = pattern.matcher(toValidate);
//
//		return match.matches();
//	}
//
//	public boolean isSpam(List<String> atributosAComprobar) {
//		boolean containsSpam = false;
//		String[] spamWords = this.systemConfigurationService
//				.findMySystemConfiguration().getSpamWords().split(",");
//		for (int i = 0; i < atributosAComprobar.size(); i++) {
//			if (containsSpam == false) {
//				for (String spamWord : spamWords) {
//					if (atributosAComprobar.get(i).toLowerCase()
//							.contains(spamWord.toLowerCase())) {
//						containsSpam = true;
//						break;
//					}
//				}
//			} else {
//				break;
//			}
//		}
//		return containsSpam;
//	}
//	
}
