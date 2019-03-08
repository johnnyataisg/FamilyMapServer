package Tests;

import DataAccess.AuthTokenDAO;
import DataAccess.Database;
import Models.AuthToken;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthTokenDAOTest
{
    Database database;
    String token;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();

        RegisterRequest request = new RegisterRequest("johnnyataisg", "12345678", "email@email.com", "Johnny", "Pao", "m");
        RegisterResult result = new RegisterService().register(request);

        token = result.getToken();
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void insertUserAuthPass() throws Exception
    {
        AuthToken authToken = new AuthTokenDAO(database.openConnection()).retrieveAuthToken(token);
        database.closeConnection(true);

        assertNotNull(authToken);
    }

    @Test
    public void insertUserAuthFail() throws Exception
    {
        AuthToken authToken = new AuthTokenDAO(database.openConnection()).retrieveAuthToken("abc");
        database.closeConnection(true);

        assertNull(authToken);
    }

    @Test
    public void retrieveAuthTokenPass() throws Exception
    {
        AuthToken authToken = new AuthTokenDAO(database.openConnection()).retrieveAuthToken(token);
        database.closeConnection(true);

        assertNotNull(authToken);
    }

    @Test
    public void retrieveAuthTokenFail() throws Exception
    {
        AuthToken authToken = new AuthTokenDAO(database.openConnection()).retrieveAuthToken("abc");
        database.closeConnection(true);

        assertNull(authToken);
    }
}
