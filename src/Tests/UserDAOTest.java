package Tests;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Models.User;
import org.junit.*;
import static org.junit.Assert.*;

public class UserDAOTest
{
    Database database;
    User user;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();

        user = new User("johnnyataisg",
                "helloworld",
                "johnnypao@outlook.com",
                "Johnny",
                "Pao",
                "m",
                "10101");

        database.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void insertPass() throws Exception
    {
        User compareUser = null;
        database.clearTables();

        try
        {
            UserDAO uDAO = new UserDAO(database.openConnection());
            uDAO.insert(user);
            compareUser = uDAO.find("johnnyataisg");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }

        assertNotNull(compareUser);
        assertEquals(user, compareUser);
    }

    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        database.clearTables();

        try
        {
            UserDAO uDAO = new UserDAO(database.openConnection());
            uDAO.insert(user);
            User newUser = new User("johnnyataisg",
                    "asdfghj",
                    "asdfasdf",
                    "Kim",
                    "Kardashian",
                    "f",
                    "000001");
            uDAO.insert(newUser);
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        User compareUser = user;
        try
        {
            UserDAO uDAO = new UserDAO(database.openConnection());
            compareUser = uDAO.find(user.getUsername());
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNull(compareUser);
    }

    @Test
    public void findPass() throws Exception
    {
        User findResult = null;
        database.clearTables();
        try
        {
            UserDAO uDAO = new UserDAO(database.openConnection());
            uDAO.insert(user);
            findResult = uDAO.find("johnnyataisg");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNotNull(findResult);
        assertEquals(user, findResult);
    }

    @Test
    public void findFail() throws Exception
    {
        User findResult = null;
        database.clearTables();
        try
        {
            UserDAO uDAO = new UserDAO(database.openConnection());
            findResult = uDAO.find("johnnyataisg");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNull(findResult);
    }

    @Test
    public void authenticatePass() throws Exception
    {
        new UserDAO(database.openConnection()).insert(user);
        database.closeConnection(true);

        boolean isUser = new UserDAO(database.openConnection()).authenticate("johnnyataisg", "helloworld");
        database.closeConnection(true);

        assertTrue(isUser);
    }

    @Test
    public void authenticateFail() throws Exception
    {
        new UserDAO(database.openConnection()).insert(user);
        database.closeConnection(true);

        boolean isUser = new UserDAO(database.openConnection()).authenticate("johnnyataisg", "12345678");
        database.closeConnection(true);

        assertFalse(isUser);
    }
}
