package Models;
import java.sql.Connection;

/**
 * A class for events
 */
public class Event
{
    /**
     * Unique id for this event
     */
    private String eventID;
    /**
     * Descendant this event belongs to
     */
    private String descendant;
    /**
     * PersonID of the person this event belongs to
     */
    private String person;
    /**
     * Latitude of location of event
     */
    private String latitude;
    /**
     * Longtitude of location of event
     */
    private String longtitude;
    /**
     * Country of location of event
     */
    private String country;
    /**
     * City of location of event
     */
    private String city;
    /**
     * Type of event, ex: baptism, birth, death, etc.
     */
    private String eventType;
    /**
     * Year of event
     */
    private String year;

    /**
     * Default constructor to create an empty Event object
     */
    public Event()
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

    public void setCity()
    {
        //To be implemented later
    }

    public void setCountry()
    {
        //To be implemented later
    }

    public void setDescendant()
    {
        //To be implemented later
    }

    public void setEventID()
    {
        //To be implemented later
    }

    public void setEventType()
    {
        //To be implemented later
    }

    public void setLatitude()
    {
        //To be implemented later
    }

    public void setLongtitude()
    {
        //To be implemented later
    }

    public void setPersonID()
    {
        //To be implemented later
    }

    public void setYear()
    {
        //To be implemented later
    }
}
