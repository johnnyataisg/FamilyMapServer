package Models;

public class Event
{
    private String eventID;
    private String descendant;
    private String person;
    private float latitude;
    private float longtitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    public Event() {}

    public Event(String eventID, String descendant, String person, float latitude, float longtitude, String country, String city, String eventType, int year)
    {
        this.eventID = eventID;
        this.descendant = descendant;
        this.person = person;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID()
    {
        return this.eventID;
    }

    public String getDescendant()
    {
        return this.descendant;
    }

    public String getPerson()
    {
        return this.person;
    }

    public float getLatitude()
    {
        return this.latitude;
    }

    public float getLongtitude()
    {
        return this.longtitude;
    }

    public String getCountry()
    {
        return this.country;
    }

    public String getCity()
    {
        return this.city;
    }

    public String getEventType()
    {
        return this.eventType;
    }

    public int getYear()
    {
        return this.year;
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
