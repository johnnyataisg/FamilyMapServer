package Tests;

import DataAccess.Database;
import Requests.RegisterRequest;
import Results.ClearResult;
import Services.ClearService;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClearTest
{
    Database database;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();

        RegisterRequest request = new RegisterRequest("johnnyataisg", "HwP072696", "johnnypao43@gmail.com", "Johnny", "Pao", "m");
        new RegisterService().register(request);
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void clearPass() throws Exception
    {
        ClearResult result = new ClearService().clear();
        assertEquals("Clear succeeded.", result.getMessage());
    }

    @Test
    public void clearFail() throws Exception
    {
        ClearResult result = new ClearService().clear();
        ClearResult result2 = new ClearService().clear();

        assertEquals(result.getMessage(), result2.getMessage());
    }
}
