package controllers;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PositionService;
import domain.Actor;
import domain.Position;

@Controller
@RequestMapping("/position/administrator")
public class PositionController extends AbstractController {

	// Services

	@Autowired
	private PositionService positionService;

	@Autowired
	private ActorService actorService;

	// Constructor

	public PositionController() {
		super();
	}

	// Actions

	/* Listing */

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Locale locale) {
		ModelAndView res;
		Actor principal;
		Collection<Position> positions;
		Boolean err;
		String language;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"ADMINISTRATOR"));

			positions = this.positionService.findAll();
			language = locale.getLanguage();

			res = new ModelAndView("position/list");
			res.addObject("positions", positions);
			res.addObject("language", language);
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {
			res = new ModelAndView("position/list");
			err = true;

			res.addObject("errMsg", oopsie);
			res.addObject("err", err);
		}
		return res;
	}

	/* Creating a position */

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Actor principal;
		Position position;
		Boolean err;

		try {
			principal = this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"ADMINISTRATOR"));

			position = this.positionService.create();

			res = this.createEditModelAndView(position);
		} catch (IllegalArgumentException oops) {
			res = new ModelAndView("misc/403");
		} catch (Throwable oopsie) {

			// TODO Página de error

			res = new ModelAndView("position/list");
			err = true;

			res.addObject("errMsg", oopsie);
			res.addObject("err", err);
		}
		return res;
	}

	/* Editing a position */

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionID) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionID);
		Assert.notNull(position);

		result = this.createEditModelAndView(position);
		return result;
	}

	/* Saving a position */

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Position position,
			@RequestParam("nameES") String nameES,
			@RequestParam("nameEN") String nameEN, BindingResult binding) {
		ModelAndView res;
		Actor principal;

		position = this.positionService.reconstruct(position, nameES, nameEN,
				binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(position, binding.toString());
		} else {
			try {
				principal = this.actorService.findByPrincipal();
				Assert.isTrue(this.actorService.checkAuthority(principal,
						"ADMINISTRATOR"));

				this.positionService.save(position);

				res = new ModelAndView("redirect:list.do");
			} catch (IllegalArgumentException oops) {
				res = new ModelAndView("misc/403");
			} catch (Throwable oopsie) {
				res = this.createEditModelAndView(position, binding.toString());
			}
		}
		return res;
	}

	// Manage methods
	protected ModelAndView createEditModelAndView(Position position) {
		ModelAndView res;

		res = createEditModelAndView(position, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Position position,
			String messageCode) {
		ModelAndView res;

		res = new ModelAndView("position/edit");
		res.addObject("position", position);
		res.addObject("message", messageCode);

		return res;
	}
}
