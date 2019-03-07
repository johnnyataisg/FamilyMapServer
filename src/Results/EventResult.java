package Results;

public class EventResult
{
    private String descendant;
    private String eventID;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;
    private String message;

    public EventResult() {}

    public EventResult(String desc, String eveID, String perID, double lat, double lon, String coun, String cit, String eveType, int yr)
    {
        this.descendant = desc;
        this.eventID = eveID;
        this.personID = perID;
        this.latitude = String.valueOf(lat);
        this.longitude = String.valueOf(lon);
        this.country = coun;
        this.city = cit;
        this.eventType = eveType;
        this.year = String.valueOf(yr);
    }

    public EventResult(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return this.message;
    }
}
