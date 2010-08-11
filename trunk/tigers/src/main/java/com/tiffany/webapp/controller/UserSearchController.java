package com.tiffany.webapp.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import com.tiffany.Constants;
import com.tiffany.service.UserManager;
import com.tiffany.model.User;
import com.tiffany.model.Role;
import com.tiffany.webapp.controller.UserSearch;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.userdetails.UsernameNotFoundException;

import java.util.*;

public class UserSearchController extends BaseFormController {
	private UserManager userManager = null;
	
	public UserSearchController() {
		setCommandClass(UserSearch.class);
		setCommandName("search");
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		log.debug("UserSearchController: formBackingObject");
		UserSearch search = new UserSearch();
		return search;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {
		log.debug("UserSearchController: onSubmit");
		
		String success = getSuccessView();
		UserSearch search = (UserSearch)command;
		String username = search.getUsername().trim();
		String companyName = search.getCompanyName().trim();
		String[] roles = search.getRoles();
		
		List<User> userList = new ArrayList();
		
		// username + companyName
		if (!username.equals("") && !companyName.equals("")) {
			log.debug("username + companyName");
			userList = userManager.findUserByUsernameAndCompanyName(username, companyName);
		// companyName
		} else if (username.equals("") && !companyName.equals("")) {
			log.debug("companyName");
			userList = userManager.findUsersByCompanyName(companyName);
		// username
		} else if (!username.equals("") && companyName.equals("")) {
			log.debug("username");
			userList.add(userManager.getUserByUsername(username));
		// nothing
		} else {
			log.debug("nothing");
			userList = userManager.getUsers(new User());
		}
		//======= check role engine ===========
		if (roles != null && !userList.isEmpty()) {
			log.debug("checking roles...");
			Iterator users = userList.iterator();			
			while (users.hasNext()) {
				boolean inRole = false;
				User user = (User)users.next();
				log.debug("user:"+user.getUsername());
				for (String roleName : roles) {
					Role role = new Role();
					role.setName(roleName);
					if (user.getRoles().contains(role)) {
						inRole = true; 
					}
				}
				if (!inRole) { users.remove(); }				
			}
		}
		//=====================================
		ModelAndView mv = new ModelAndView(success, getCommandName(), command);
        mv.addObject(Constants.USER_LIST, userList);
        //mv.addObject("firstTime", "nup");
        return mv;
	}
}
