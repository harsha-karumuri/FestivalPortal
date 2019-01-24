package com.accenture.adf.businesstier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;
import com.accenture.adf.helper.FERSDbQuery;

/**
 * 
 * <br/>
 * CLASS DESCRIPTION:<br/>
 * A Data Access Object (DAO) class for handling and managing visitor related
 * data requested, used, and processed in the application and maintained in the
 * database. The interface between the application and visitor data persisting
 * in the database.
 * 
 */

public class VisitorDAO {

	// LOGGER for handling all transaction messages in VISITORDAO
	private static Logger log = Logger.getLogger(VisitorDAO.class);

	// JDBC API classes for data persistence
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private FERSDbQuery query;

	// Default constructor for injecting Spring dependencies for SQL queries
	public VisitorDAO() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		query = (FERSDbQuery) context.getBean("SqlBean");
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to loading visitor details into VISITOR table in database<br/>
	 * and validating about existing visitor details before inserting a visitor
	 * <br/>
	 * 
	 * <br/>
	 * PSEUDOCODE: <br/>
	 * Create a connection to database<br/>
	 * Prepare a statement object using the connection that uses a query that
	 * inserts visitor information <br/>
	 * into the visitor table <br/>
	 * Execute a statement object selects all the usernames from the visitor
	 * table<br/>
	 * if the username is not in the visitor table <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return boolean
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */

	public boolean insertData(Visitor visitor) throws ClassNotFoundException, SQLException, Exception {

		connection = FERSDataConnection.createConnection();
		boolean userFound = false;
		Statement stmt = connection.createStatement();
		resultSet = stmt.executeQuery(query.getValidateVisitor());
		while (resultSet.next()) {

			if (resultSet.getString("USERNAME").equals(visitor.getUserName())) {
				userFound = true;
				break;
			}
		}

		if (userFound == false) {

			statement = connection.prepareStatement(query.getInsertQuery());
			statement.setString(1, visitor.getUserName());
			statement.setString(2, visitor.getPassword());
			statement.setString(3, visitor.getFirstName());
			statement.setString(4, visitor.getLastName());
			statement.setString(5, visitor.getEmail());
			statement.setString(6, visitor.getPhoneNumber());
			statement.setString(7, visitor.getAddress());

			int insertRows = statement.executeUpdate();

			if (insertRows != 0) {
				resultSet.close();
				connection.close();
				return true;
			} else {
				throw new FERSGenericException("Some problem while inserting the visitor records", new Exception());
			}

		}
		connection.close();
		return false;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method for searching for visitor details using USERNAME and
	 * PASSWORD<br/>
	 * 
	 * <br/>
	 * PSEUDOCODE: <br/>
	 * Create a connection to database<br/>
	 * Prepare a statement object using the connection<br/>
	 * that uses a query that retrieves all the data from the visitor table
	 * based on the username and password provided. Execute the query and <br/>
	 * Using a WHILE LOOP, store the results in the result set record in the
	 * visitor object.<br/>
	 * 
	 * @param username
	 *            (type String)
	 * @param password
	 *            (type String)
	 * 
	 * @return Visitor
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * 
	 */
	public Visitor searchUser(String username, String password) throws ClassNotFoundException, SQLException 
	{
				// TODO: Add code here.....
				// TODO: Pseudo-code are in the block comments above this method
				// TODO: For more comprehensive pseudo-code with details, refer to the
				// Component/Class Detailed Design Document
		Visitor visitor = null;
		connection = FERSDataConnection.createConnection();
		statement=connection.prepareStatement(query.getSearchQuery());
		statement.setString(1, username);
		statement.setString(2,password);
		resultSet=statement.executeQuery();
		while(resultSet.next())
		{
			 visitor = new Visitor();
			 visitor.setUserName(resultSet.getString("username"));
			 visitor.setPassword(resultSet.getString("password"));
			 visitor.setFirstName(resultSet.getString("firstname"));
			 visitor.setLastName(resultSet.getString("lastname"));
			 visitor.setVisitorId(resultSet.getInt("visitorid"));
			visitor.setEmail(resultSet.getString("email"));
			visitor.setPhoneNumber(resultSet.getString("phonenumber"));
			visitor.setAddress(resultSet.getString("address"));
				
		}
		connection.close();
		return visitor;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO method to register visitor to specific event and checking about
	 * status of visitor to particular event. <br/>
	 * 
	 * PSEUDO-CODE: <br/>
	 * Create a connection to the database <br/>
	 * Prepare a statement object using the connection: that inserts the visitor
	 * and event IDs into the EVENTSESSIONSIGNUP table <br/>
	 * Execute the query to perform the update <br/>
	 * 
	 * 
	 * @param visitor
	 * @param eventid
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */

	public void registerVisitorToEvent(Visitor visitor, int eventid)
			throws ClassNotFoundException, SQLException, Exception {

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getRegisterQuery());
		statement.setInt(2,visitor.getVisitorId());
		statement.setInt(1,eventid );
		int rows=statement.executeUpdate();
	
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to display all the events registered by particular
	 * visitor<br/>
	 * 
	 * PSEUDO-CODE: <br/>
	 * Create a connection to the database <br/>
	 * Prepare a statement object using the connection: that returns the event
	 * information for all the events that are registered to a visitor<br/>
	 * Execute the query to retrieve the results into a result set<br/>
	 * Place each event record‘s information in an event list. <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return Collection of Event Arrays (type Event)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */
	public ArrayList<Event> registeredEvents(Visitor visitor) throws ClassNotFoundException, SQLException {

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method
		// TODO: For more comprehensive pseudo-code with details, refer to the
		// Component/Class Detailed Design Document

		ArrayList<Event> eventList = new ArrayList<Event>();
		log.info("All Events retreived from Database :" + eventList);
		connection = FERSDataConnection.createConnection();
		statement= connection.prepareStatement(query.getStatusQuery());
		statement.setInt(1, visitor.getVisitorId());
		resultSet=statement.executeQuery();
		while(resultSet.next())
		{
			
			Event event=new Event();
			event.setEventid(resultSet.getInt("eventid"));
			event.setName(resultSet.getString("name"));
			event.setDescription(resultSet.getString("description"));
			event.setPlace(resultSet.getString("places"));
			event.setDuration(resultSet.getString("duration"));
			event.setEventtype(resultSet.getString("eventtype"));
			//event.setSeatsavailable(resultSet.getInt("seatsavailable"));
			event.setSignupid(resultSet.getInt("signupid"));
			
			
			
			eventList.add(event);
			
			
		}
		connection.close();	
		
		return eventList;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO method to update visitor with additional information <br/>
	 * <br/>
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * 
	 * @return int
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * 
	 */
	public int updateVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getUpdateQuery());
		statement.setString(1, visitor.getFirstName());
		statement.setString(2, visitor.getLastName());
		statement.setString(3, visitor.getUserName());
		statement.setString(4, visitor.getPassword());
		statement.setString(5, visitor.getEmail());
		statement.setString(6, visitor.getPhoneNumber());
		statement.setString(7, visitor.getAddress());
		statement.setInt(8, visitor.getVisitorId());

		int status = statement.executeUpdate();
		log.info("Updating visitor details in Database for Visitor ID :" + visitor.getVisitorId());
		FERSDataConnection.closeConnection();
		return status;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO method to unregister from events <br/>
	 * 
	 * 
	 * @param visitor
	 *            (type Visitor)
	 * @param eventid
	 *            (type int)
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */

	public void unregisterEvent(Visitor visitor, int eventid) throws ClassNotFoundException, SQLException, Exception {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getDeleteEventQuery());
		statement.setInt(1, eventid);
		statement.setInt(2, visitor.getVisitorId());
		int status = statement.executeUpdate();
		if (status <= 0)
			throw new FERSGenericException("Records not updated properly", new Exception());
		log.info("unregistering event in Database for the visitor :" + visitor.getFirstName());
		FERSDataConnection.closeConnection();
	}

}