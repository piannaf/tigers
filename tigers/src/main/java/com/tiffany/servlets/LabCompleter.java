package com.tiffany.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tiffany.dao.UserDao;
import com.tiffany.model.User;
/**
 * This class provides the backend functionality for autocompletion of the waterbody field
 *
 * @author Jane
 */
public class LabCompleter extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Log log = LogFactory.getLog(LabCompleter.class);
	static UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
 		this.userDao = userDao;
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		String labPrefix = request.getParameter("tag");
 
		List<String> waterbodies = findWaterbodies(labPrefix);
	 
		request.setAttribute("waterbodies", waterbodies);
				 
		RequestDispatcher dispatcher = 			
						request.getRequestDispatcher("/AutoCompleteWaterbody.jsp");
		dispatcher.include(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<String> findWaterbodies(String labPrefix) {
		 
			List<String> names = new ArrayList<String>();
			
			List<User> laboratories = userDao.getLaboratoriesLike(labPrefix);
			log.debug("\n\t !!!!! "+laboratories);
			
			if (laboratories != null) {
				for(User lab : laboratories) {			
					names.add(lab.getUsername());
				}
			}
						
			return(names);
	}

}
