package Tests;

import DataAccess.Database;
import Requests.RegisterRequest;
import Results.PersonAllResult;
import Results.RegisterResult;
import Services.PersonAllService;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonAllTest
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
    public void personAllPass() throws Exception
    {
        PersonAllResult result = new PersonAllService().person(token);
        assertNull(result.getMessage());
        assertTrue(result.getData().size() > 1);
    }

    @Test
    public void personAllFail() throws Exception
    {
        PersonAllResult result = new PersonAllService().person("asdfsdfd");
        assertEquals("Invalid authentication token", result.getMessage());
    }
}
