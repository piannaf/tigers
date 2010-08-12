package com.tiffany.dao;

import java.util.List;

import com.tiffany.model.Contractor;

public class ContractorDaoTest extends BaseDaoTestCase{ 
	
	  private ContractorDao contractorDao;

	  /**
	   * Spring will automatically inject WaterbodyDao object on startup
	   * @param ContractorDao
	   */
	  public void setContractorDao(ContractorDao contractorDao) {
	      this.contractorDao = contractorDao;
	  }

	  /**
	   * Test the save method
	   *
	   */
	  public void testSave(){
	     	    
		  Contractor c = new Contractor();
		  c.setUsername("USER3");
	      c.setName("North Atlantic");
	      c.setAddress("7 Overthere Avenue");
	      c.setContact("Mr Ed");
	      c.setEmail("g@q");
	      c.setPhone("5432123");
	    
	      contractorDao.save(c);
	      flush();
	      
	      c = contractorDao.get("USER3");
	      assertNotNull(c.getName());
	       
	  }
	    public void testExists() throws Exception {
	        boolean b = contractorDao.exists("USER2");
	        assertTrue(b);
	    }
	    
	    public void testNotExists() throws Exception {
	        boolean b = contractorDao.exists("L");
	        assertFalse(b);
	    }
	    
	    public void testgetAll() throws Exception {
	    	List con = contractorDao.getAll();
	    	assertNotNull(con);
	    	assertTrue(con.size() == 2);
	}

}
