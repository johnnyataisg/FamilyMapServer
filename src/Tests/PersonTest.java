package Tests;

import DataAccess.Database;
import Requests.RegisterRequest;
import Results.PersonResult;
import Results.RegisterResult;
import Services.PersonService;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest
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
    public void personPass() throws Exception
    {
        PersonResult result = new PersonService().person(personID, token);
        assertEquals("johnnyataisg", result.getDescendant());
    }

    @Test
    public void personFail() throws Exception
    {
        PersonResult result = new PersonService().person(personID, "asdfasdfa");
        assertEquals("Invalid authentication token", result.getMessage());
    }
}
