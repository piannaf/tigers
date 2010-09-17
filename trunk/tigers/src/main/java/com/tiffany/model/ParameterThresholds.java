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
	private Waterbody waterbody;
	private ParameterNames parameter;
	private BigDecimal min;
	private BigDecimal max;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "waterbody")
	public Waterbody getWaterbody() {
		return waterbody;
	}
	public void setWaterbody(Waterbody name) {
		this.waterbody = name;
	}
	
	@ManyToOne
	@JoinColumn(name = "parameter")
	public ParameterNames getParameter() {
		return parameter;
	}
	public void setParameter(ParameterNames parameter) {
		this.parameter = parameter;
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
		return new ToStringBuilder(this).append("id", id).append("name", waterbody).append("parameter_id",
				parameter).append("min", min).append("max", max).toString();
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
		return new EqualsBuilder().append(waterbody, other.waterbody)
				.append(parameter, other.parameter).append(min, other.min).append(max,
						other.max).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(591165033, -1663524385).append(id).append(waterbody)
				.append(parameter).append(min).append(max).toHashCode();
	}

}
