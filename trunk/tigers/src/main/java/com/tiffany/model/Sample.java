package com.tiffany.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import java.math.BigDecimal;

/**
 * This class represents the "sample" object in TiGERS
 *
 * @author CSSE3004GC
 */
@Entity
@Table(name="sample")
public class Sample extends BaseObject {
    
    private Long id;
    private Date date_taken;
    private BigDecimal ph;                    
    private BigDecimal ec;   
    private BigDecimal temperature;
    private BigDecimal collar_depth;
    private BigDecimal arsenic;
    private BigDecimal grease;
    private BigDecimal fluoride;
    private BigDecimal chromium;
    private User laboratory;
    private Sampler sampler;
        
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	@Column(nullable=false)
	public Date getDate_taken() {
		return date_taken;
	}
	public void setDate_taken(Date dateTaken) {
		this.date_taken = dateTaken;
	}
	
	@Column(precision=5, scale=2)
	public BigDecimal getPh() {
		return ph;
	}
	public void setPh(BigDecimal ph) {
		this.ph = ph;
	}

	@Column(precision=5, scale=0)
	public BigDecimal getEc() {
		return ec;
	}	
	public void setEc(BigDecimal ec) {
		this.ec = ec;
	}

	@Column(precision=5, scale=1)
	public BigDecimal getTemperature() {
		return temperature;
	}
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	@Column(precision=5, scale=0)
	public BigDecimal getCollar_depth() {
		return collar_depth;
	}
	public void setCollar_depth(BigDecimal collarDepth) {
		collar_depth = collarDepth;
	}

	@Column(precision=5, scale=3)
	public BigDecimal getArsenic() {
		return arsenic;
	}
	public void setArsenic(BigDecimal arsenic) {
		this.arsenic = arsenic;
	}

	@Column(precision=3, scale=0)
	public BigDecimal getGrease() {
		return grease;
	}
	public void setGrease(BigDecimal grease) {
		this.grease = grease;
	}

	@Column(precision=5, scale=3)
	public BigDecimal getFluoride() {
		return fluoride;
	}
	public void setFluoride(BigDecimal fluoride) {
		this.fluoride = fluoride;
	}

	@Column(precision=5, scale=3)
	public BigDecimal getChromium() {
		return chromium;
	}
	public void setChromium(BigDecimal chromium) {
		this.chromium = chromium;
	}

	@ManyToOne
	@JoinColumn(name = "laboratory")
	public User getLaboratory() {
		return laboratory;
	}
	public void setLaboratory(User laboratory) {
		this.laboratory = laboratory;
	}

	@ManyToOne
	@JoinColumn(name = "sampler")
	public Sampler getSampler() {
		return sampler;
	}

	public void setSampler(Sampler sampler) {
		this.sampler = sampler;
	}
	
	// converts a value to string, taking into consideration special formatting rules
	// eg, null -> "NaN"; -0.05 -> "<0.05", 2.5 -> "2.5"
	public static String getValueDisplayString(BigDecimal value) {
		if(value == null) return "NaN";
		if(value.compareTo(new BigDecimal(0)) < 0)
			return "<" + value.abs().toString();
		return value.toString();
	}
	// reverse of the above
	public static BigDecimal getDisplayStringValue(String value) {
		// not part of the spec, but just in case...
		if(value == null) return null;
		
		value = value.trim();
		if(value.toLowerCase().equals("nan")) return null;
		if(value.charAt(0) == '<')
			return new BigDecimal(value.substring(1).trim()).negate();
		return new BigDecimal(value);
	}
	// for easy use on JSP
	@Transient public String getPhString()				{return getValueDisplayString(ph); }
	@Transient public String getEcString()				{return getValueDisplayString(ec); }
	@Transient public String getTemperatureString()		{return getValueDisplayString(temperature); }
	@Transient public String getCollar_depthString()	{return getValueDisplayString(collar_depth); }
	@Transient public String getArsenicString()			{return getValueDisplayString(arsenic); }
	@Transient public String getGreaseString()			{return getValueDisplayString(grease); }
	@Transient public String getFluorideString()		{return getValueDisplayString(fluoride); }
	@Transient public String getChromiumString()		{return getValueDisplayString(chromium); }
	
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Sample))
			return false;
		Sample castOther = (Sample) other;
		return new EqualsBuilder().append(date_taken, castOther.date_taken)
				.append(ph, castOther.ph).append(ec, castOther.ec).append(sampler,
						castOther.sampler).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(591165033, -1663524385).append(date_taken)
				.append(ph).append(ec).append(sampler).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("date_taken",
				date_taken).append("ph", ph).append("ec", ec)
				.append("tag", sampler).toString();
	}
	

}
