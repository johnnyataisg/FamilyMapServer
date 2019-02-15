import java.sql.Connection;

/**
 * A class for events
 * <pre>
 *     <b>Domain</b>:
 *      eventID: String -- Unique id for event
 *      descendant: String -- The descendant this event belongs to
 *      person: String -- The person this event belongs to
 *      latitude: String -- Event location
 *      longtitude: String -- Event location
 *      country: String -- Country of event
 *      city: String -- City of event
 *      eventType: String -- Type of event, birth, death, baptism, etc.
 *      year: String -- Time of event
 * </pre>
 */
public class Event
{
    private String eventID;
    private String descendant;
    private String person;
    private String latitude;
    private String longtitude;
    private String country;
    private String city;
    private String eventType;
    private String year;

    /**
     * Creates an SQL statement with the eventID in the where clause and takes the returned values and sets it to the data members in this instance
     * @param eventID
     * @param conn
     */
    public Event(String eventID, Connection conn)
    {
        //To be implemented later
    }

    public String getEventID()
    {
        //To be implemented later
        return "";
    }

    public String getDescendant()
    {
        //To be implemented later
        return "";
    }

    public String getPerson()
    {
        //To be implemented later
        return "";
    }

    public String getLatitude()
    {
        //To be implemented later
        return "";
    }

    public String getLongtitude()
    {
        //To be implemented later
        return "";
    }

    public String getCountry()
    {
        //To be implemented later
        return "";
    }

    public String getCity()
    {
        //To be implemented later
        return "";
    }

    public String getEventType()
    {
        //To be implemented later
        return "";
    }

    public String getYear()
    {
        //To be implemented later
        return "";
    }
}
