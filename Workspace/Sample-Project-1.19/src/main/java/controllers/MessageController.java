package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message/actor")
public class MessageController extends AbstractController{

	//Services

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageBoxService messageBoxService;



	//Create ----------------------------------------------------------------------

	@RequestMapping(value="/create", method= RequestMethod.GET)
	public ModelAndView create(){
		final ModelAndView result;
		Message mensaje;

		mensaje = this.messageService.create();

		result = this.createEditModelAndView(mensaje);

		return result;
	}

	//Edition ------------------------------------------------------------


	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId){
		ModelAndView result;
		Message message;

		message = this.messageService.findOne(messageId);
		Assert.notNull(message);

		result = this.createEditModelAndView(message);

		return result;


	}


	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid final Message mensaje, final BindingResult binding){
		ModelAndView result;
		Message message;

		message = this.messageService.reconstruct(mensaje, binding);

		if(binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try{
				this.messageService.save(message);
				result = new ModelAndView("redirect:/messagebox/list.do");
			}catch(final Throwable oops){
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		return result;

	}

	@RequestMapping(value="/edit",method = RequestMethod.POST, params="move")
	public ModelAndView move(@Valid final Message mensaje, final BindingResult binding){
		ModelAndView result;
		MessageBox destination;

		Message message;

		message = this.messageService.reconstruct(mensaje, binding);


		if(binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try{

				destination = message.getMessageBoxes().iterator().next();
				this.messageService.move(message, destination);
				result = new ModelAndView("redirect:/messagebox/list.do");
			}catch (final Throwable oops){
				result = this.createEditModelAndView(message, "message.commit.error");

			}

		return result;



	}

	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(final Message mensaje, final BindingResult binding){
		ModelAndView result;
		
		Message message;

		message = this.messageService.reconstruct(mensaje, binding);
		
		if(binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try{
				this.messageService.delete(message);
				result = new ModelAndView("redirect:/messagebox/list.do");

			}catch(Throwable oops){
				result = this.createEditModelAndView(message, "message.commit.error");

			}

		return result;
	}
	
	//Ancillary methods ------------------------------------------------------
	
	
	protected ModelAndView createEditModelAndView(final Message mensaje){
		ModelAndView result;

		result = this.createEditModelAndView(mensaje, null);

		return result;

	}
	
	
	protected ModelAndView createEditModelAndView(final Message mensaje, String messageError){
		ModelAndView result;
		Collection<MessageBox> boxes,messageBoxes;
		Collection<Message> messages;
		Actor sender;
		boolean possible = false;
		Actor principal;
		Date sentMoment;
		Collection<Actor> recipients;
		
		principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);


		messages = new ArrayList<Message>();
		recipients = new ArrayList<Actor>();
		
		boxes = this.messageBoxService.findByOwner(principal.getId());
		
		for(MessageBox mb : boxes){
			messages.addAll(mb.getMessages());
			if(mb.getMessages().contains(mensaje) && mb.getOwner().equals(principal)){
				possible = true;
				
			}
		}


		sentMoment = mensaje.getSentMoment();
		messageBoxes = mensaje.getMessageBoxes();
		sender = mensaje.getSender();
		
		recipients = this.actorService.findAllExceptPrincipal();

		result = new ModelAndView("message/edit");
		result.addObject("sentMoment", sentMoment);
		result.addObject("messageBoxes", messageBoxes);
		result.addObject("sender", sender);
		result.addObject("mensaje", mensaje);
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "message/actor/edit.do");
		result.addObject("possible", possible);
		result.addObject("broadcast", false);
		result.addObject("message", messageError);
		result.addObject("recipients", recipients);
		


		return result;
	}
}
