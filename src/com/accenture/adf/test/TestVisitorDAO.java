package com.accenture.adf.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;

/**
 * JUnit test case for VisitorDAO class for testing all repository methods to
 * call database sub-routines
 * 
 */
public class TestVisitorDAO {
	
	private Visitor visitor;
	private VisitorDAO visitorDAO;
	private ArrayList<Event> registeredEvents;

	/**
	 * Setting up initial objects 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		visitor = new Visitor();
		visitorDAO = new VisitorDAO();
		registeredEvents = new ArrayList<Event>();
	}

	/**
	 * Deallocating objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		visitor = null;
		visitorDAO = null;
		registeredEvents = null;
	}

	/**
	 * Test case for method insertData
	 */
	@Test
	public void testInsertData() {
		/**
		 * @TODO: Create visitor object by setting appropriate values
		 * Call insertData method by passing this visitor object
		 * Search this new visitor object by calling searchUser method
		 * Assert the values of username
		 */	
		
		visitor.setUserName("TestVisitor1");
		visitor.setFirstName("TestVFname");
		visitor.setLastName("TestVLname");
		visitor.setPassword("ttt");
		visitor.setPhoneNumber("2344");
		visitor.setAddress("TestPlace");		
		try {
			visitorDAO.insertData(visitor);
			
			visitor = visitorDAO.searchUser("TestVisitor1", "ttt");
		} catch (SQLException exception) {			
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {			
			fail("Class Not Found Exception");
		} catch (Exception exception) {			
			fail("NULL Exception");
		}
		
		assertEquals(true, visitor.getUserName().equals("TestVisitor1"));
	}	

	/**
	 * Test case for method searchUser
	 */
	@Test
	public void testSearchUser() {
		/**
		 * @TODO: Call searchUser method for valid values of username
		 * and password and assert the value of username for the returned type of method
		 */	
		
		String userName = "ylee";
		String password = "password";
		try {
			visitor = visitorDAO.searchUser(userName, password);
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {
			fail("Class Not Found Exception");
		} catch (Exception exception) {
			fail("NULL Exception");
		}
		assertEquals("ylee", visitor.getUserName()); 
	}

	/**
	 * Test case for method registerVisitorToEvent
	 */
	@Test
	public void testRegisterVisitorToEvent() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to registerVisitorToEvent method
		 * and assert the value
		 */	
		try {
			visitor = visitorDAO.searchUser("ylee", "password");
			visitorDAO.registerVisitorToEvent(visitor, 1001);
			registeredEvents = visitorDAO.registeredEvents(visitor);
			for (Event event : registeredEvents) {
				assertEquals(1001, event.getEventid());
				break;
			}			
		} catch (SQLException exception) {			
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {
			fail("Class Not Found Exception");
		} catch (Exception exception) {
			fail("NULL Exception");
		}
	}	

	/**
	 * Test case for method registeredEvents
	 */
	@Test
	public void testRegisteredEvents() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to registeredEvents method
		 * and assert the value
		 */	
		try {
			visitor = visitorDAO.searchUser("ylee", "password");
			registeredEvents = visitorDAO.registeredEvents(visitor);
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {
			fail("Class Not Found Exception");
		}
		for (Event event : registeredEvents) {
			assertEquals(1001, event.getEventid());
			break;
		}
	}

	/**
	 * Test case for method updateVisitor
	 */
	@Test
	public void testUpdateVisitor() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Update the value in this visitor object
		 * Pass this visitor object to updateVisitor method
		 * and assert the value of changed value
		 */	
		
		int updateStatus = 0;
		try {
			visitor = visitorDAO.searchUser("npatel", "password");
			updateStatus = visitorDAO.updateVisitor(visitor);
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {			
			fail("Class Not Found Exception");
		}
		assertEquals(1, updateStatus);
	}

	/**
	 * Test case for method registeredEvents
	 */
	@Test
	public void testUnregisterEvent() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to unregisterEvent method
		 * and assert the value
		 */		
	
		try {
			visitor = visitorDAO.searchUser("harsha", "password");
			visitorDAO.unregisterEvent(visitor, 1001);	
			registeredEvents = visitorDAO.registeredEvents(visitor);
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {			
			fail("Class Not Found Exception");
		} catch (Exception exception) {
			fail("NULL Exception");
		}
		for (Event event : registeredEvents) {
			assertTrue(1001 != event.getEventid());
			break;
		}
	}

}
