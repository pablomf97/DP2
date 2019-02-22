
package forms;

import java.util.Collection;

import org.hibernate.validator.constraints.NotBlank;

public class BrotherhoodForm extends ActorForm {

	private String				title;
	private Collection<String>	pictures;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

}
