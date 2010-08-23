package com.tiffany.model;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import java.math.BigDecimal;

/**
 * This class represents the "ParameterThresholds" object in TiGERS
 *
 */
@Entity
@Table(name="parameterthresholds")
public class ParameterThresholds extends BaseObject {
	
	private Long id;
	private String name;
	private Long parameter_id;
	private BigDecimal min;
	private BigDecimal max;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, length=30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public Long getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(Long parameter_id) {
		this.parameter_id = parameter_id;
	}

	@Column(precision=9, scale=6)
	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	
	@Column(precision=9, scale=6)
	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).append("parameter_id",
				parameter_id).append("min", min).append("max", max).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParameterThresholds other = (ParameterThresholds) obj;
		return new EqualsBuilder().append(name, other.name)
				.append(parameter_id, other.parameter_id).append(min, other.min).append(max,
						other.max).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(591165033, -1663524385).append(id).append(name)
				.append(parameter_id).append(min).append(max).toHashCode();
	}

}
