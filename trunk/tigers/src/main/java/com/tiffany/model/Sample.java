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
    private Short ph;                    
    private Short ec;   
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
		date_taken = dateTaken;
	}
	
	@Column
	public Short getPh() {
		return ph;
	}
	public void setPh(Short ph) {
		this.ph = ph;
	}
	
	@Column
	public Short getEc() {
		return ec;
	}	
	public void setEc(Short ec) {
		this.ec = ec;
	}
	
	@Column
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
