package controllers;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SystemConfigurationService;
import domain.Actor;
import domain.SystemConfiguration;

@Controller
@RequestMapping("/sysconfig/administrator")
public class SystemConfigurationController extends AbstractController {

	// Services

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	@Autowired
	private ActorService actorService;

	// Constructor

	public SystemConfigurationController() {
		super();
	}

	// Actions

	/* Display */

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView res;
		Actor principal;
		SystemConfiguration sysConfig;
		String[] spamWords;
		String[] negativeWords;
		String[] positiveWords;
		String[] messagePriority;
		Map<String, String> welcomeMessage;
		Boolean err;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"ADMINISTRATOR"));

			sysConfig = this.systemConfigurationService
					.findMySystemConfiguration();

			spamWords = sysConfig.getSpamWords().split(",");
			negativeWords = sysConfig.getNegativeWords().split(",");
			positiveWords = sysConfig.getPossitiveWords().split(",");
			messagePriority = sysConfig.getMessagePriority().split(",");
			welcomeMessage = sysConfig.getWelcomeMessage();

			res = new ModelAndView("sysConfig/display");
			res.addObject("sysConfig", sysConfig);
			res.addObject("spamWords", spamWords);
			res.addObject("negativeWords", negativeWords);
			res.addObject("positiveWords", positiveWords);
			res.addObject("messagePriority", messagePriority);
			res.addObject("welcomeMessage", welcomeMessage);

		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("sysConfig/display");
			err = true;

			res.addObject("errMsg", oopsie);
			res.addObject("err", err);
		}
		return res;
	}

	// Editing sysConfig

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "systemconfigurationID")
	public ModelAndView edit(@RequestParam int systemconfigurationID,
			Locale locale) {
		ModelAndView res;
		SystemConfiguration systemConfiguration;

		systemConfiguration = this.systemConfigurationService
				.findOne(systemconfigurationID);
		Assert.notNull(systemConfiguration);
		res = createEditModelAndView(systemConfiguration, locale);

		return res;
	}

	protected ModelAndView createEditModelAndView(
			SystemConfiguration systemConfiguration, Locale locale) {
		ModelAndView res;

		res = createEditModelAndView(systemConfiguration, null, locale);

		return res;
	}

	protected ModelAndView createEditModelAndView(
			SystemConfiguration systemConfiguration, String messageCode,
			Locale locale) {
		ModelAndView res;

		res = new ModelAndView("sysConfig/edit");
		res.addObject("sysConfig", systemConfiguration);
		res.addObject("message", messageCode);
		res.addObject("locale", locale);

		return res;
	}

	// Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(SystemConfiguration systemConfiguration,
			@RequestParam("nameES") String nameES,
			@RequestParam("nameEN") String nameEN, BindingResult binding,
			Locale locale) {
		ModelAndView res;

		systemConfiguration = this.systemConfigurationService.reconstruct(
				systemConfiguration, nameES, nameEN, binding);
		if (binding.hasErrors()) {
			res = createEditModelAndView(systemConfiguration,
					binding.toString(), locale);
		} else {
			try {
				this.systemConfigurationService.save(systemConfiguration);
				res = new ModelAndView("redirect:display.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(systemConfiguration,
						oops.getMessage(), locale);
			}
		}
		return res;
	}

}