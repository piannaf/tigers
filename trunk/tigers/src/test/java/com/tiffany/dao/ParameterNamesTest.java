package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.Waterbody;

public class ParameterNamesTest extends BaseDaoTestCase{ 
	
	  private ParameterNamesDao ParameterNamesDao;

	  /**
	   * Spring will automatically inject ParameterNamesDao object on startup
	   * @param ParameterNamesDao
	   */
	  public void setParameterNamesDao(ParameterNamesDao ParameterNamesDao) {
	      this.ParameterNamesDao = ParameterNamesDao;
	  }

	    public void testExists() throws Exception {
	        boolean b = ParameterNamesDao.exists(5L);
	        assertTrue(b);
	    }
	    
	    public void testNotExists() throws Exception {
	        boolean b = ParameterNamesDao.exists(10L);
	        assertFalse(b);
	    }
	    
	    public void testgetAll() throws Exception {
	    	List pn = ParameterNamesDao.getAll();
	    	assertNotNull(pn);
	    	assertTrue(pn.size() == 8);
	}
	    public void testfindbytype() throws Exception {
	    	List pns = ParameterNamesDao.findByType('B');       
	    	assertTrue(pns.size() == 5);
	    	pns = ParameterNamesDao.findByType('S');       
	    	assertTrue(pns.size() == 2);
	    	pns = ParameterNamesDao.findByType('G');       
	    	assertTrue(pns.size() == 1);
	    }
}
