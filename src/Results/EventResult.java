package Results;

/**
 * A class for event service results
 */
public class EventResult
{
    /**
     * Name of user this event belongs to
     */
    private String descendant;

    /**
     * Event's unique ID
     */
    private String eventID;

    /**
     * Person's unique ID
     */
    private String personID;

    /**
     * Latitude of event location
     */
    private float latitude;

    /**
     * Longtitude of event location
     */
    private float longtitude;

    /**
     * Country of event location
     */
    private String country;

    /**
     * City of event location
     */
    private String city;

    /**
     * Type of event
     */
    private String eventType;

    /**
     * Year of event
     */
    private int year;

    /**
     * Creates an event service result object
     * @param desc
     * @param eveID
     * @param perID
     * @param lat
     * @param lon
     * @param coun
     * @param cit
     * @param eveType
     * @param yr
     */
    public EventResult(String desc, String eveID, String perID, float lat, float lon, String coun, String cit, String eveType, int yr)
    {

    }

    public String getDescendant()
    {
        return null;
    }

    public String getEventID()
    {
        return null;
    }

    public String getPersonID()
    {
        return null;
    }

    public float getLatitude()
    {
        return 0;
    }

    public float getLongtitude()
    {
        return 0;
    }

    public String getCountry()
    {
        return null;
    }

    public String getCity()
    {
        return null;
    }

    public String getEventType()
    {
        return null;
    }

    public int getYear()
    {
        return 0;
    }
}
