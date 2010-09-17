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

	private Long id;
	private String name;
	private String internal_name;
	private char type;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return new ToStringBuilder(this).append("id", id).append("name",
				name).append("type", type).toString();
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result
		    + ((internal_name == null) ? 0 : internal_name.hashCode());
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    result = prime * result + type;
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
	    if (internal_name == null) {
		if (other.internal_name != null)
		    return false;
	    } else if (!internal_name.equals(other.internal_name))
		return false;
	    if (name == null) {
		if (other.name != null)
		    return false;
	    } else if (!name.equals(other.name))
		return false;
	    if (type != other.type)
		return false;
	    return true;
	}


}
