package Tests;

import DataAccess.Database;
import DataAccess.EventDAO;
import Models.Event;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Services.RegisterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EventDAOTest
{
    Database database;
    String personID;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();
        database.createTables();

        RegisterRequest request = new RegisterRequest("johnnyataisg", "12345678", "email@email.com", "Johnny", "Pao", "m");
        RegisterResult result = new RegisterService().register(request);

        personID = result.getPersonID();
    }

    @After
    public void tearDown() throws Exception
    {
        database.clearTables();
    }

    @Test
    public void retrieveFamilyEventsPass() throws Exception
    {
        List<Event> events = new EventDAO(database.openConnection()).retrieveFamilyEvents("johnnyataisg");
        database.closeConnection(true);

        assertTrue(events.size() > 1);
    }

    @Test
    public void retrieveFamilyEventsFail() throws Exception
    {
        List<Event> events = new EventDAO(database.openConnection()).retrieveFamilyEvents("random");
        database.closeConnection(true);

        assertNull(events);
    }

    @Test
    public void retrieveEventPass() throws Exception
    {
        Event newEvent = new Event("10101", "johnnyataisg", personID, 10.1, 20.1, "United States", "Provo", "SomeType", 2019);
        new EventDAO(database.openConnection()).insertEvent(newEvent);
        database.closeConnection(true);

        Event findEvent = new EventDAO(database.openConnection()).retrieveEvent("10101");
        database.closeConnection(true);

        assertEquals(newEvent.getEventID(), findEvent.getEventID());
    }

    @Test
    public void retrieveEventFail() throws Exception
    {
        Event newEvent = new Event("10101", "johnnyataisg", personID, 10.1, 20.1, "United States", "Provo", "SomeType", 2019);
        new EventDAO(database.openConnection()).insertEvent(newEvent);
        database.closeConnection(true);

        Event findEvent = new EventDAO(database.openConnection()).retrieveEvent("10100");
        database.closeConnection(true);

        assertNull(findEvent);
    }

    @Test
    public void retrieveBirthEventPass() throws Exception
    {
        Event birth = new EventDAO(database.openConnection()).retrieveBirthEvent(personID);
        database.closeConnection(true);

        assertNotNull(birth);
    }

    @Test
    public void retrieveBirthEventFail() throws Exception
    {
        Event birth = new EventDAO(database.openConnection()).retrieveBirthEvent("11111");
        database.closeConnection(true);

        assertNull(birth);
    }

    @Test
    public void insertEventPass() throws Exception
    {
        Event newEvent = new Event("10101", "johnnyataisg", personID, 10.1, 20.1, "United States", "Provo", "SomeType", 2019);
        new EventDAO(database.openConnection()).insertEvent(newEvent);
        database.closeConnection(true);

        Event findEvent = new EventDAO(database.openConnection()).retrieveEvent("10101");
        database.closeConnection(true);

        assertNotNull(findEvent);
    }

    @Test
    public void insertEventFail() throws Exception
    {
        Event newEvent = new Event("10101", "johnnyataisg", personID, 10.1, 20.1, "United States", "Provo", "SomeType", 2019);
        new EventDAO(database.openConnection()).insertEvent(newEvent);
        database.closeConnection(true);

        Event findEvent = new EventDAO(database.openConnection()).retrieveEvent("10100");
        database.closeConnection(true);

        assertNull(findEvent);
    }

    @Test
    public void clearDataPass() throws Exception
    {
        new EventDAO(database.openConnection()).clearData("johnnyataisg");
        database.closeConnection(true);

        List<Event> events = new EventDAO(database.openConnection()).retrieveFamilyEvents("johnnyataisg");
        database.closeConnection(true);

        assertNull(events);
    }

    @Test
    public void clearDataFail() throws Exception
    {
        new EventDAO(database.openConnection()).clearData("someoneElse");
        database.closeConnection(true);

        List<Event> events = new EventDAO(database.openConnection()).retrieveFamilyEvents("johnnyataisg");
        database.closeConnection(true);

        assertNotNull(events);
    }
}
