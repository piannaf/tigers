package com.tiffany.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

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
    private Double ph;                    
    private Double ec;   
    private Double temperature;
    private Double collar_depth;
    private Double arsenic;
    private Double grease;
    private Double fluoride;
    private Double chromium;
    private String username;
    private String tag;
        
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column
	public Date getDate_taken() {
		return date_taken;
	}
	public void setDate_taken(Date dateTaken) {
		this.date_taken = dateTaken;
	}
	
	@Column
	public Double getPh() {
		return ph;
	}
	public void setPh(Double ph) {
		this.ph = ph;
	}
	
	@Column
	public Double getEc() {
		return ec;
	}	
	public void setEc(Double ec) {
		this.ec = ec;
	}

	@Column
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	@Column
	public Double getCollar_depth() {
		return collar_depth;
	}
	public void setCollar_depth(Double collarDepth) {
		collar_depth = collarDepth;
	}

	@Column
	public Double getArsenic() {
		return arsenic;
	}
	public void setArsenic(Double arsenic) {
		this.arsenic = arsenic;
	}

	@Column
	public Double getGrease() {
		return grease;
	}
	public void setGrease(Double grease) {
		this.grease = grease;
	}

	@Column
	public Double getFluoride() {
		return fluoride;
	}
	public void setFluoride(Double fluoride) {
		this.fluoride = fluoride;
	}

	@Column
	public Double getChromium() {
		return chromium;
	}
	public void setChromium(Double chromium) {
		this.chromium = chromium;
	}

	@Column(length = 20)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(length = 5)
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Sample))
			return false;
		Sample castOther = (Sample) other;
		return new EqualsBuilder().append(date_taken, castOther.date_taken)
				.append(ph, castOther.ph).append(ec, castOther.ec).append(tag,
						castOther.tag).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(591165033, -1663524385).append(date_taken)
				.append(ph).append(ec).append(tag).toHashCode();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("date_taken",
				date_taken).append("ph", ph).append("ec", ec)
				.append("tag", tag).toString();
	}
	

}
