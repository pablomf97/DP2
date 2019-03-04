
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageBoxRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	@Autowired
	private ActorService			actorService;

	//	@Autowired
	//	private MessageService			messageService;
	@Autowired
	private Validator				validator;


	public MessageBox create(final Actor actor) {
		final MessageBox b = new MessageBox();
		b.setOwner(actor);
		b.setIsPredefined(false);
		b.setMessages(new ArrayList<Message>());
		b.setParentMessageBoxes(new ArrayList<MessageBox>());
		return b;
	}

	public MessageBox save(final MessageBox box) {
		// We search boxes from logged actor
		MessageBox saved;
		if (box.getId() != 0) {
			final MessageBox boxBD = this.findOne(box.getId());
			if (boxBD.getIsPredefined() == false)
				boxBD.setMessages(box.getMessages());
			else {
				if ((this.actorService.findByPrincipal().equals(box.getOwner())))
					boxBD.setName(box.getName());
				boxBD.setMessages(box.getMessages());
			}
			saved = this.messageBoxRepository.save(boxBD);
		} else
			saved = this.messageBoxRepository.save(box);
		return saved;
	}

	public Collection<MessageBox> findAll() {
		return this.messageBoxRepository.findAll();
	}

	public MessageBox findOne(final int idBox) {
		final MessageBox box = this.messageBoxRepository.findOne(idBox);
		return box;
	}

	public void delete(final MessageBox messageBox) {
		Assert.isTrue((this.actorService.findByPrincipal().equals(messageBox.getOwner())), "Box not belong to the logged actor");
		Assert.isTrue((messageBox.getIsPredefined()), "Box undeleteable");
		if (messageBox.getMessages() != null) {
			if (!messageBox.getParentMessageBoxes().isEmpty())
				for (final MessageBox b : messageBox.getParentMessageBoxes())
					this.delete(b);
			for (final Message m : messageBox.getMessages()) {

			}
			//TODO: cuando esté mensajes hecho
			//this.messageService.deleteMessages(m, messageBox);
		}
		this.messageBoxRepository.delete(messageBox);
	}
	//Other requirements
	public void initializeDefaultBoxes(final Actor a) {

		final MessageBox in = this.create(a);
		in.setIsPredefined(false);
		in.setName("IN");
		this.save(in);

		final MessageBox trash = this.create(a);
		trash.setIsPredefined(false);
		trash.setName("TRASH");
		this.save(trash);

		final MessageBox out = this.create(a);
		out.setIsPredefined(false);
		out.setName("OUT");
		this.save(out);

		final MessageBox spam = this.create(a);
		spam.setIsPredefined(false);
		spam.setName("SPAM");
		this.save(spam);

		final MessageBox notification = this.create(a);
		spam.setIsPredefined(false);
		spam.setName("NOTIFICATION");
		this.save(notification);

	}

	public Collection<MessageBox> findByOwner(final int actorId) {
		return this.messageBoxRepository.boxesByActor(actorId);
	}
	/**
	 * First level of boxes
	 * 
	 * @param actorId
	 * @return
	 */
	public Collection<MessageBox> findByOwnerFirst(final int actorId) {
		return this.messageBoxRepository.firstBoxesByActor(actorId);
	}

	public MessageBox findByName(final int actorId, final String name) {
		return this.messageBoxRepository.boxByName(actorId, name);
	}

	public MessageBox reconstruct(final MessageBox messageBox, final BindingResult binding) {
		final Actor actor = this.actorService.findByPrincipal();
		MessageBox result = this.create(actor);
		if (messageBox.getId() == 0) {
			result.setName(messageBox.getName());
			this.validator.validate(result, binding);
		} else {
			final MessageBox bd = this.messageBoxRepository.findOne(messageBox.getId());
			result.setName(messageBox.getName());
			this.validator.validate(result, binding);
			if (!binding.hasErrors()) {
				bd.setName(messageBox.getName());
				result = bd;
			}
		}
		return result;
	}
	public boolean checkUniqueBox(final String name) {
		boolean bool = true;
		final Actor actor = this.actorService.findByPrincipal();
		if (this.findByName(actor.getId(), name) != null)
			bool = false;
		return bool;
	}
}
