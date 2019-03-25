package Tests;

import DataAccess.Database;
import DataAccess.UserDAO;
import Models.User;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class RegisterTest
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
    public void registerPass() throws Exception
    {
        RegisterRequest request = new RegisterRequest("johnnyataisg", "12345678", "johnnypao43@gmail.com", "Johnny", "Pao", "m");
        RegisterResult actualResult = new RegisterService().register(request);

        UserDAO uDAO = new UserDAO(database.openConnection());
        User foundUser = uDAO.find("johnnyataisg");
        database.closeConnection(true);

        assertNotNull(foundUser);
    }

    @Test
    public void registerFail() throws Exception
    {
        RegisterRequest request = new RegisterRequest("johnnyataisg", "12345678", "johnnypao43@gmail.com", "Johnny", "Pao", "m");
        new RegisterService().register(request);

        RegisterResult result = new RegisterService().register(request);
        assertEquals("Username already taken by another user", result.getMessage());
    }
}
