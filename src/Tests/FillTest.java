package Tests;

import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Models.Person;
import Models.User;
import Results.FillResult;
import Services.FillService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FillTest
{
    Database database;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void fillPass() throws Exception
    {
        User newUser = new User("username", "password", "email@email.com", "fName", "lName", "m", "10101");
        new UserDAO(database.openConnection()).insert(newUser);
        database.closeConnection(true);

        Person newPerson = new Person("10101", "username", "fName", "lName", "m", null, null, null);
        new PersonDAO(database.openConnection()).insert(newPerson);
        database.closeConnection(true);

        FillResult result = new FillService().fill("username", 4);
        assertNotEquals("Invalid username or generations parameter", result.getMessage());
    }

    @Test
    public void fillFail() throws Exception
    {
        FillResult result = new FillService().fill("username", 4);
        assertEquals("Invalid username or generations parameter", result.getMessage());
    }
}
