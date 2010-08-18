package com.tiffany.webapp.controller;

import java.math.BigDecimal;

public class SamplerForm {
	/**
	 * This class represents the "sampler form" object in TiGERS
	 * Used by validation classes.
	 *
	 * @author Jane
	 */

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
	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getCollar_height() {
		return collar_height;
	}

	public void setCollar_height(BigDecimal collar_height) {
		this.collar_height = collar_height;
	}
	public String getComp_screening_freq() {
		return comp_screening_freq;
	}

	public void setComp_screening_freq(String comp_screening_freq) {
		this.comp_screening_freq = comp_screening_freq;
	}
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

}
