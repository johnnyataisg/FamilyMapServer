package DataAccess;

import Models.Event;

import java.sql.Connection;
import java.util.List;

/**
 * A class for reading and writing in the Event table in the database
 */
public class EventDAO
{
    /**
     * A database connection instance
     */
    private Connection connection;

    /**
     * Default constructor to create an empty EventDAO
     */
    public EventDAO()
    {
        //To be implemented
    }

    /**
     * Creates an EventDAO object and sets its database connection
     * @param conn A database connection object
     */
    public EventDAO(Connection conn)
    {
        //To be implemented
    }

    /**
     * Looks up a personID in the database and retrieves all events related to that person
     * @param personID
     * @return
     */
    public List<Event> retrieveEvents(String personID)
    {
        //To be implemented
        return null;
    }

    /**
     * Looks up the eventID in the database and retrieves the event with that ID
     * @param eventID
     * @return
     */
    public Event retrieveEvent(String eventID)
    {
        //To be implemented
        return null;
    }

    /**
     * Inserts an event into the database if the eventID doesn't exist, else return false
     * @param event
     * @return
     */
    public boolean insertEvent(Event event)
    {
        //To be implemented
        return true;
    }

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
