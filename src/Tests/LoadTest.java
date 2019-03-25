package Tests;

import DataAccess.Database;
import DataAccess.UserDAO;
import Models.MaleNames;
import Models.User;
import Requests.LoadRequest;
import Results.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LoadTest
{
    Database database;
    LoadRequest request;

    @Before
    public void setUp() throws Exception
    {
        Scanner scanner = new Scanner(new File("src/JSON/example.json"));
        StringBuilder str = new StringBuilder();
        while (scanner.hasNextLine())
        {
            str.append(scanner.nextLine());
        }
        request = new Gson().fromJson(str.toString(), LoadRequest.class);

        database = new Database();
        database.createTables();
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void loadPass() throws Exception
    {
        LoadResult result = new LoadService().load(request);

        User findUser = new UserDAO(database.openConnection()).find("sheila");
        database.closeConnection(true);
        assertNotNull(findUser);
    }

    @Test
    public void loadFail() throws Exception
    {
        boolean success = true;
        try
        {
            request = null;
            LoadResult result = new LoadService().load(request);
        }
        catch (Exception e)
        {
            success = false;
        }
        assertFalse(success);
    }
}
