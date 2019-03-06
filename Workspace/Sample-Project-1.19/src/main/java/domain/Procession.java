package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
 @Index( columnList ="ticker,title,description,organisedMoment,isDraft,brotherhood")
 })
public class Procession extends DomainEntity {

	/* Attributes */
	private String ticker;
	private String title;
	private String description;
	private Date organisedMoment;
	private Integer maxCols;
	private boolean isDraft;
	// Float es un tipo ya predefinido en java, por lo que por ahora Float pasa
	// a ser Platform.
	private Collection<Platform> platforms;
	private Brotherhood brotherhood;

	/* Getters&Setters */

	@NotBlank
	@Pattern(regexp = "\\d{6}-[A-Z]{5}")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOrganisedMoment() {
		return organisedMoment;
	}

	public void setOrganisedMoment(Date organisedMoment) {
		this.organisedMoment = organisedMoment;
	}
	
	@NotNull
	@Min(value = 1)
	public Integer getMaxCols() {
		return maxCols;
	}

	public void setMaxCols(Integer maxCols) {
		this.maxCols = maxCols;
	}

	public boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(boolean isDraft) {
		this.isDraft = isDraft;
	}

	@Valid
	@ManyToMany
	public Collection<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Collection<Platform> platforms) {
		this.platforms = platforms;
	}

	@Valid
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return brotherhood;
	}

	public void setBrotherhood(Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((brotherhood == null) ? 0 : brotherhood.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isDraft ? 1231 : 1237);
		result = prime * result
				+ ((organisedMoment == null) ? 0 : organisedMoment.hashCode());
		result = prime * result
				+ ((platforms == null) ? 0 : platforms.hashCode());
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Procession other = (Procession) obj;
		if (brotherhood == null) {
			if (other.brotherhood != null)
				return false;
		} else if (!brotherhood.equals(other.brotherhood))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (isDraft != other.isDraft)
			return false;
		if (organisedMoment == null) {
			if (other.organisedMoment != null)
				return false;
		} else if (!organisedMoment.equals(other.organisedMoment))
			return false;
		if (platforms == null) {
			if (other.platforms != null)
				return false;
		} else if (!platforms.equals(other.platforms))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

}