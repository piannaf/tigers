package com.tiffany.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * This class represents both the Screening Frequency and the Frequency Item entities
 * as the item "belongs" to the frequency.
 */
@Entity
@Table(name="screeningfrequency")
public class ScreeningFrequency extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String description;
	private String frequency;
	private String tag;
	private Set<ParameterNames> parameterNames = new HashSet<ParameterNames>(0);
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(nullable=false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(nullable=false)
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	@Column(nullable=false)
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setParameterNames(Set<ParameterNames> parameterNames) {
		this.parameterNames = parameterNames;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "frequencyitem", joinColumns = { @JoinColumn(name = "frequency_id") },
								inverseJoinColumns = { @JoinColumn(name = "parameter_id") })
	public Set<ParameterNames> getParameterNames() {
		return parameterNames;
	}
	@Override
	public String toString() {
		return "ScreeningFrequency [id=" + id + ", description=" + description
				+ ", frequency=" + frequency + ", tag=" + tag + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScreeningFrequency other = (ScreeningFrequency) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
