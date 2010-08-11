package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import com.tiffany.model.LabelValue;

public class UserSearch {
	private String companyName;
	private String username;
	private String[] roles;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	@Transient
	public List<LabelValue> getRoleList() {
		List<LabelValue> roleList = new ArrayList<LabelValue>();
		if (this.roles != null) {
			for (String role : roles) {
				roleList.add(new LabelValue(role, role));
			}
		}
		return roleList;
	}
}
