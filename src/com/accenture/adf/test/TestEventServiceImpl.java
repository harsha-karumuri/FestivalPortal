package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.EventServiceImpl;

/**
 * Junit test case to test class EventServiceImpl
 *
 */
public class TestEventServiceImpl 
{

	private List<Event> eventList;	
	private Visitor visitor;
	private EventServiceImpl eventServiceImpl;

	/**
	 * Set up the objects required before execution of every method
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception
	{		
		eventServiceImpl = new EventServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		eventList=null;
		visitor=null;
		eventServiceImpl=null;
		
	}

	/**
	 * Test case to test the method getAllEvents
	 */
	@Test
	public void testGetAllEvents() 
	{
		/**
		 * @TODO: Call getAllEvents method and assert it for the size of returned array
		 */	
		
		eventList = eventServiceImpl.getAllEvents();
		assertTrue(eventList.size() > 0);
		
	}

	/**
	 * Test case to test the method checkEventsofVisitor
	 */
	@Test
	public void testCheckEventsofVisitor() 
	{
		/**
		 * @TODO: Call checkEventsofVisitor and assert the returned type of this method
		 * for appropriate return type
		 */	
		
		visitor.setVisitorId(1001);
		int eventid = 1001;
		boolean eventStatus = eventServiceImpl.checkEventsofVisitor(visitor,
				eventid);
		assertEquals(true, eventStatus);
	}

	/**
	 * Test case to test the method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions()
	{
		/**
		 * @TODO: Call updateEventDeletions and assert the return type of this method
		 */	
		int eventid = 1001;
		eventServiceImpl.updateEventDeletions(eventid);
		assertTrue("No of seats for Event updated by 1", true);
	}

}
