package com.tiffany.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class represents the "sampler" object in TiGERS
 *
 * @author Jane
 */
@Entity
@Table(name="sampler")
public class Sampler extends BaseObject {
	
	private String tag;
	private String license;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private BigDecimal collar_height;
	private String comp_screening_freq; 
	private String purpose;
	private String contractor;
	private String waterbody; 
	private String laboratory;
	private String depth_to_collar_screening_freq;	
		
	@Id @Column(nullable=false)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	@Column(precision=9, scale=6)
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	@Column(precision=9, scale=6)
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	@Column(precision=5, scale=0)
	public BigDecimal getCollar_height() {
		return collar_height;
	}

	public void setCollar_height(BigDecimal collar_height) {
		this.collar_height = collar_height;
	}
	@Column(nullable=false)
	public String getComp_screening_freq() {
		return comp_screening_freq;
	}

	public void setComp_screening_freq(String comp_screening_freq) {
		this.comp_screening_freq = comp_screening_freq;
	}
	@Column(nullable=false)
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	@Column(nullable=false)
	public String getWaterbody() {
		return waterbody;
	}

	public void setWaterbody(String waterbody) {
		this.waterbody = waterbody;
	}

	public String getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}

	public String getDepth_to_collar_screening_freq() {
		return depth_to_collar_screening_freq;
	}

	public void setDepth_to_collar_screening_freq(
			String depth_to_collar_screening_freq) {
		this.depth_to_collar_screening_freq = depth_to_collar_screening_freq;
	}
	
	//Returns a map of all parameter values and names
	public Map<String, Object> list_all_params() {
	    Map<String, Object> allParams = new HashMap<String, Object>();
	    allParams.put("Collar Height", collar_height);
	    allParams.put("Contractor", contractor);
	    allParams.put("Comprehensive Screening Frequency", comp_screening_freq);
	    allParams.put("Depth to Collar Screening Frequency", depth_to_collar_screening_freq);
	    allParams.put("Laboratory", laboratory);
	    allParams.put("License", license);
	    allParams.put("Purpose", purpose);
	    allParams.put("Water Body", waterbody);
	    allParams.put("Longitude", longitude);
	    allParams.put("Latitude", latitude);
	    
	    return allParams;
	}
	
	//public Boolean is_ok_to_delete() {
	//    return this.collar_height
	//}

	@Override
 	public String toString() {
		return new ToStringBuilder(this).append("tag", tag).append("purpose",
				purpose).append("waterbody", waterbody).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sampler other = (Sampler) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

}
