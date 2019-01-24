package com.accenture.adf.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.VisitorServiceImpl;

/**
 * Junit test class for VisitorServiceImpl
 *
 */
public class TestVisitorServiceImpl {

	private List<Event> visitorList;	
	private Visitor visitor;
	private VisitorServiceImpl visitorServiceImpl;

	/**
	 * Set up the initial methods 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {		
		visitorServiceImpl = new VisitorServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		visitorServiceImpl = null;
		visitor = null;
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testCreateVisitor() {
		/**
		 * @TODO: Set the appropriate values for visitor object and
		 * call the method createVisitor by passing an argument of this visitor 
		 * object and then asserting the returned type of this method
		 */	
		
		visitor.setUserName("TestVisitor4");
		visitor.setFirstName("TestVFname1");
		visitor.setLastName("TestVLname2");
		visitor.setPassword("password");
		visitor.setPhoneNumber("2344");
		visitor.setAddress("TestPlace");
		boolean status = visitorServiceImpl.createVisitor(visitor);
		Assert.assertEquals(false, status);
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testSearchVisitor() {
		/**
		 * @TODO: Call searchVisitor method by passing the appropriate arguments 
		 * and then asserting the returned type visitor username with the argument passed
		 */	
		visitor = visitorServiceImpl.searchVisitor("TestVisitor4", "password");
		Assert.assertEquals("TestVisitor4", visitor.getUserName());
	}

	/**
	 * Test case for method RegisterVisitor
	 */
	@Test
	public void testRegisterVisitor() {
		/**
		 * @TODO: Call RegisterVisitor method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of RegisterVisitor method 
		 */	
		visitor = visitorServiceImpl.searchVisitor("TestVisitor4", "password");
		visitorServiceImpl.RegisterVisitor(visitor, 1002);
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() > 0);
	}

	/**
	 * Test case for method showRegisteredEvents
	 */
	@Test
	public void testShowRegisteredEvents() {
		/**
		 * @TODO: Call showRegisteredEvents method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of showRegisteredEvents method 
		 */	
		visitor = visitorServiceImpl.searchVisitor("ylee", "password");
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() > 0);
	}

	/**
	 * Test case for method updateVisitorDetails
	 */
	@Test
	public void testUpdateVisitorDetails() {
		/**
		 * @TODO: Call updateVisitorDetails method by passing the visitor object which
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of updateVisitorDetails
		 */	
		visitor = visitorServiceImpl.searchVisitor("TestVisitor4", "password");
		visitor.setFirstName("radha");
		int status = visitorServiceImpl.updateVisitorDetails(visitor);
		Assert.assertEquals(1, status);
	}

	/**
	 * Test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent() {
		/**
		 * @TODO: Call unregisterEvent method by passing the visitor object which can be
		 * retrieved using searchVisitor method and then asserting the returned type 
		 * of unregisterEvent
		 */	
		visitor = visitorServiceImpl.searchVisitor("TestVisitor4", "password");
		visitorServiceImpl.unregisterEvent(visitor, 1003);
		visitorList = visitorServiceImpl.showRegisteredEvents(visitor);
		Assert.assertTrue(visitorList.size() > 0);
	}

}
