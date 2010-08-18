package com.tiffany.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiffany.model.Waterbody;

public class WaterbodyDaoTest  extends BaseDaoTestCase{ 
	
	  private WaterbodyDao waterbodyDao;

	  /**
	   * Spring will automatically inject WaterbodyDao object on startup
	   * @param WaterbodyDao
	   */
	  public void setWaterbodyDao(WaterbodyDao waterbodyDao) {
	      this.waterbodyDao = waterbodyDao;
	  }

	  /**
	   * Test the save method
	   *
	   */
	  public void testSave(){
	     	    
		  Waterbody w = new Waterbody();
	      w.setName("Atlantic");
	      w.setType('S');
	    
	      waterbodyDao.save(w);
	      flush();
	      
	      w = waterbodyDao.get(2L);
	      assertNotNull(w.getName());
	       
	  }
	    public void testWbExists() throws Exception {
	        boolean b = waterbodyDao.exists(1L);
	        assertTrue(b);
	    }
	    
	    public void testUserNotExists() throws Exception {
	        boolean b = waterbodyDao.exists(10L);
	        assertFalse(b);
	    }
	    
	    public void testgetAll() throws Exception {
	    	List wbs = waterbodyDao.getAll();
	    	assertNotNull(wbs);
	    	assertTrue(wbs.size() == 3);
	}
	    public void testfindLikeName() throws Exception {
	    	List wbs = waterbodyDao.findLikeName("A");       
	    	assertTrue(wbs.size() == 2);
	    	wbs = waterbodyDao.findLikeName("aquifer 6");       
	    	assertTrue(wbs.size() == 1);
	}

}
