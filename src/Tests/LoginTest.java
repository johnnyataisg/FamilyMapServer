package Tests;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Models.AuthToken;
import Models.User;
import Requests.LoginRequest;
import Results.LoginResult;
import Services.LoginService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest
{
    Database database;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();

        User newUser = new User("johnnyataisg", "12345678", "johnnypao43@mgmail.com", "Johnny", "Pao", "m", "10101");
        new UserDAO(database.openConnection()).insert(newUser);
        database.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void loginPass() throws Exception
    {
        LoginRequest request = new LoginRequest("johnnyataisg", "12345678");
        LoginResult result = new LoginService().login(request);

        AuthToken token = null;
        try
        {
            token = new AuthTokenDAO(database.openConnection()).retrieveAuthToken(result.getAuthToken());
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNotNull(token);
    }

    @Test
    public void loginFail() throws Exception
    {
        LoginRequest request = new LoginRequest("WrongUsername", "WrongPassword");
        LoginResult result = new LoginService().login(request);

        assertEquals("Invalid username or password", result.getMessage());
    }
}
