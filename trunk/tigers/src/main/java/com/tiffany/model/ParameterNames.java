package com.tiffany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@Table(name="parameternames")
public class ParameterNames extends BaseObject {

	private Long parameter_id;
	private String name;
	private String internal_name;
	private char type;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getParameter_id() {
		return parameter_id;
	}

	public void setParameter_id(Long parameter_id) {
		this.parameter_id = parameter_id;
	}
	@Column(nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable=false, length=20)
	public String getInternal_name() {
		return internal_name;
	}
	public void setInternal_name(String internal_name) {
		this.internal_name = internal_name;
	}
	
	@Column(nullable=false)
	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("parameter_id", parameter_id).append("name",
				name).append("type", type).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameter_id == null) ? 0 : parameter_id.hashCode());
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
		ParameterNames other = (ParameterNames) obj;
		if (parameter_id == null) {
			if (other.parameter_id != null)
				return false;
		} else if (!parameter_id.equals(other.parameter_id))
			return false;
		return true;
	}

}