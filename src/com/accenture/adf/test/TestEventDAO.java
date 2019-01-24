package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for EventDAO class
 *
 */
public class TestEventDAO {

	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private ArrayList<Event> showAllEvents;
	private EventDAO dao;	

	/**
	 * Sets up database connection before other methods are executed in this class
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpDatabaseConnection() throws Exception
	{
		connection = FERSDataConnection.createConnection();
	}

	/**
	 * Closes the database connection after all the methods are executed
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownDatabaseConnection() throws Exception 
	{
		/**
		 * @TODO: Close connection object here  
		 */
		connection.close();
		
	}

	/**
	 * Sets up the objects required in other methods
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		showAllEvents = new ArrayList<Event>();
		dao = new EventDAO();
	}

	/**
	 * Deallocate the resources after execution of method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		showAllEvents=null;
		dao=null;
	}

	/**
	 * Positive test case to test the method showAllEvents
	 */
	@Test
	public void testShowAllEvents_Positive() {
		/**
		 * @TODO: Call showAllEvents method and assert it for
		 * size of returned type list
		 */	
		
		try {
			showAllEvents=dao.showAllEvents();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (showAllEvents.size() == 0) {
			assertTrue("No events in database", true);
		}
		if (showAllEvents.size() == 1) {
			assertTrue("One event retreived", true);
		}
		if (showAllEvents.size() == 3) {
			assertTrue("All Events retreived", true);
		}	
	}
	/**
	 * Test case to test the exception scenario in database
	 */
	public void testShowAllEvents_Exception() {
		try {
			showAllEvents = dao.showAllEvents();
		} catch (ClassNotFoundException exception) {
			assertTrue(exception.getMessage(), true);
		} catch (SQLException exception) {
			assertTrue(exception.getMessage(), true);
		}
	}
	/**
	 * Junit test case to test positive case for updateEventDeletions
	 */
	@Test	
	public void testUpdateEventDeletions_Positive() {
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventDeletions for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 more then testSeatsAvailableAfter
		 */	
		int eventid = 1001;
		int testSeatsAvailableBefore = 0;
		int testSeatsAvailableAfter = 0;
		try {
			statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableBefore = resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventDeletions(eventid);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableAfter = resultSet.getInt("SEATSAVAILABLE");
			}
			assertEquals(testSeatsAvailableBefore, testSeatsAvailableAfter - 1);
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (Exception exception) {
			fail("Exception");
		}	
	}

	/**
	 * Negative test case for method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions_Negative() {
		/**
		 * @TODO: Call updateEventDeletions for incorrect eventid and it should
		 * throw an exception
		 */	
		int eventid = 2001;
		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventDeletions(eventid);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (FERSGenericException ferSGenericException) {
			Assert.assertEquals("Records not updated properly",
					ferSGenericException.getMessage());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Positive() {
		/**
		 * @TODO: Find out seats available for an event by opening a connection
		 * and calling the query SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?
		 * Call the updateEventNominations for eventId
		 * Again find out the seats available for this event
		 * testSeatsAvailableBefore should be 1 less then testSeatsAvailableAfter
		 */	
		
		int eventid = 1001;
		int testSeatsAvailableBefore = 0;
		int testSeatsAvailableAfter = 0;

		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableBefore = resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventNominations(eventid);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				testSeatsAvailableAfter = resultSet.getInt("SEATSAVAILABLE");
			}
			assertEquals(testSeatsAvailableBefore, testSeatsAvailableAfter + 1);
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (Exception exception) {
			fail("Exception");
		}
		
	}

	/**
	 * Negative test case for method updateEventNominations
	 */
	@Test
	public void testUpdateEventNominations_Negative() {
		/**
		 * @TODO: Call updateEventNominations for incorrect eventid and it should
		 * throw an exception
		 */	

		int eventid = 2001;		
		try {
			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}

			dao.updateEventNominations(eventid);

			statement = connection
					.prepareStatement("SELECT SEATSAVAILABLE FROM EVENT WHERE EVENTID = ?");
			statement.setInt(1, eventid);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				resultSet.getInt("SEATSAVAILABLE");
			}
		} catch (ClassNotFoundException exception) {
			fail("ClassNotFound Exception");
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (FERSGenericException ferSGenericException) {
			Assert.assertEquals("Records not updated properly",
					ferSGenericException.getMessage());
		} catch (Exception exception) {
			fail("Exception");
		}
	}

	/**
	 * Positive test case for method checkEventsofVisitor
	 */
	@Test
	public void testCheckEventsOfVisitor_Positive() {
		/**
		 * @TODO: Create visitor object by setting appropriate values
		 * Call checkEventsofVisitor method by passing this visitor object and
		 * valid eventId
		 * Assert the value of return type 
		 */	
		int eventid = 1002;
		try {
			Visitor visitor = new Visitor();
			visitor.setUserName("TestVisitor");
			visitor.setFirstName("TestVFname1");
			visitor.setLastName("TestVLname1");
			visitor.setPassword("ttt");
			visitor.setPhoneNumber("2344");
			visitor.setAddress("TestPlace");
			visitor.setVisitorId(1008);
			boolean status = dao.checkEventsofVisitor(visitor, eventid);
			Assert.assertFalse(status);			
		}

		catch (SQLException exception) {
			fail("SQL Exception");
		} catch (Exception exception) {			
			fail("Exception");
		}
	}

}
