package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.adf.businesstier.controller.EventController;

/**
 * Junit test class for EventController 
 *
 */
public class TestEventController
{

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ModelAndView modelAndView;
	private EventController controller;

	/**
	 * Sets up initial objects required in other methods
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		modelAndView = new ModelAndView();
		controller = new EventController();
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest("GET", "/catalog.htm");
	}

	/**
	 * Deallocate the objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		
		modelAndView = null;
		controller = null;
		response = null;
		request=null;
	}

	/**
	 * Test case to test the positive scenario for getAvailableEvents  method
	 */
	@Test
	public void testGetAvailableEvents_Positive()
	{
		
		/**
		 * @TODO: Call getAvailableEvents methods and assert
		 * it for appropriate model view name
		 */	
		
		try {
			modelAndView = controller.getAvailableEvents(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		assertEquals("/eventCatalog.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents_Negative()
	{
		
		/**
		 * @TODO: Call getAvailableEvents methods  by passing request as null
		 * and assert it for appropriate model view name
		 */		
		
		try {
			modelAndView = controller.getAvailableEvents(null, response);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		assertEquals(null, modelAndView.getViewName());
	}

}
