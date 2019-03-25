package Tests;

import DataAccess.Database;
import DataAccess.EventDAO;
import Models.Event;
import Requests.RegisterRequest;
import Results.EventResult;
import Results.RegisterResult;
import Services.EventService;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest
{
    Database database;
    String token;
    String personID;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();

        RegisterRequest request = new RegisterRequest("johnnyataisg", "12345678", "email@email.com", "Johnny", "Pao", "m");
        RegisterResult result = new RegisterService().register(request);
        token = result.getToken();
        personID = result.getPersonID();

        Event newEvent = new Event("10101", "johnnyataisg", "01010", 10.2, 20.1, "United States", "Provo", "SomeType", 2019);
        new EventDAO(database.openConnection()).insertEvent(newEvent);
        database.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void eventPass() throws Exception
    {
        EventResult result = new EventService().event("10101", token);
        assertNull(result.getMessage());
        assertEquals("Provo", result.getCity());
    }

    @Test
    public void eventFail() throws Exception
    {
        EventResult result = new EventService().event("00000", "12345");
        assertNotNull(result.getMessage());
        assertNull(result.getCity());
    }
}
