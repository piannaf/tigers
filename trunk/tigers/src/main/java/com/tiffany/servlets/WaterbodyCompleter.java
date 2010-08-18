package com.tiffany.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tiffany.dao.WaterbodyDao;
import com.tiffany.dao.hibernate.WaterbodyDaoHibernate;
import com.tiffany.model.Waterbody;
import com.tiffany.webapp.controller.SampleController;
/**
 * This class provides the backend functionality for autocompletion of the waterbody field
 *
 * @author Jane
 */
public class WaterbodyCompleter extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Log log = LogFactory.getLog(WaterbodyCompleter.class);
	static WaterbodyDao waterbodyDao;
	
	public void setWaterbodyDao(WaterbodyDao waterbodyDao) {
 		this.waterbodyDao = waterbodyDao;
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		String waterbodyPrefix = request.getParameter("waterbody");
 
		List<String> waterbodies = findWaterbodies(waterbodyPrefix);
	 
		request.setAttribute("waterbodies", waterbodies);
				 
		RequestDispatcher dispatcher = 			
						request.getRequestDispatcher("/AutoCompleteWaterbody.jsp");
		dispatcher.include(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<String> findWaterbodies(String waterbodyPrefix) {
		 
			List<String> names = new ArrayList<String>();
			
			List<Waterbody> waterbodies = waterbodyDao.findLikeName(waterbodyPrefix);
			
			if (waterbodies != null) {
				for(Waterbody waterbody : waterbodies) {			
					names.add(waterbody.getName());
				}
			}
						
			return(names);
	}

}
