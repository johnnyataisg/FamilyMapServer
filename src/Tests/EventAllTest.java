package Tests;

import DataAccess.Database;
import Requests.RegisterRequest;
import Results.EventAllResult;
import Results.RegisterResult;
import Services.EventAllService;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventAllTest
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
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void eventAllPass() throws Exception
    {
        EventAllResult result = new EventAllService().event(token);
        assertTrue(result.getData().size() > 1);
        assertNull(result.getMessage());
    }

    @Test
    public void eventAllFail() throws Exception
    {
        EventAllResult result = new EventAllService().event("12345");
        assertNull(result.getData());
        assertNotNull(result.getMessage());
    }
}
