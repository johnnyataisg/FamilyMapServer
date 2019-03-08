package Tests;

import java.util.List;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Models.Person;
import org.junit.*;
import static org.junit.Assert.*;

public class PersonDAOTest
{
    Database database;
    Person person;
    Person father;
    Person mother;

    @Before
    public void setUp() throws Exception
    {
        database = new Database();

        person = new Person("10101",
                "Adam",
                "Johnny",
                "Pao",
                "m",
                "Arthur",
                "Terry",
                "Jina");

        father = new Person("father01", "Adam", "Father of Johnny", "Pao", "m", null, null, null);
        mother = new Person("mother01", "Adam", "Mother of Johnny", "Pao", "f", null, null, null);

        database.createTables();
    }

    @After
    public void tearDown() throws Exception {
        database.clearTables();
    }

    @Test
    public void insertPass() throws Exception
    {
        Person comparePerson = null;
        database.clearTables();
        try
        {
            PersonDAO pDAO = new PersonDAO(database.openConnection());
            pDAO.insert(person);
            comparePerson = pDAO.find("10101");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNotNull(comparePerson);
        assertEquals(person, comparePerson);
    }

    @Test
    public void insertFail() throws Exception
    {
        boolean didItWork = true;
        try
        {
            PersonDAO pDAO = new PersonDAO(database.openConnection());
            pDAO.insert(person);
            Person newPerson = new Person("10101",
                    "hello world",
                    "Drex",
                    "Effect",
                    "f",
                    "Adam",
                    "Eve",
                    "");
            pDAO.insert(newPerson);
            database.closeConnection(didItWork);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
            didItWork = false;
        }
        assertFalse(didItWork);
        Person comparePerson = person;
        try
        {
            PersonDAO pDAO = new PersonDAO(database.openConnection());
            comparePerson = pDAO.find(person.getPersonID());
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNull(comparePerson);
    }

    @Test
    public void findPass() throws Exception
    {
        Person findResult = null;
        database.clearTables();
        try
        {
            PersonDAO pDAO = new PersonDAO(database.openConnection());
            pDAO.insert(person);
            findResult = pDAO.find("10101");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNotNull(findResult);
        assertEquals(person, findResult);
    }

    @Test
    public void findFail() throws Exception
    {
        Person findResult = null;
        database.clearTables();
        try
        {
            PersonDAO pDAO = new PersonDAO(database.openConnection());
            findResult = pDAO.find("10101");
            database.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            database.closeConnection(false);
        }
        assertNull(findResult);
    }

    @Test
    public void setFatherPass() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setFather(person.getPersonID(), father.getPersonID());
        database.closeConnection(true);

        person = new PersonDAO(database.openConnection()).find(person.getPersonID());
        database.closeConnection(true);

        assertEquals(person.getFather(), father.getPersonID());
    }

    @Test
    public void setFatherFail() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setFather("aqzwsxcde", father.getPersonID());
        database.closeConnection(true);

        person = new PersonDAO(database.openConnection()).find(person.getPersonID());
        database.closeConnection(true);

        assertNotEquals(person.getFather(), father.getPersonID());
    }

    @Test
    public void setMotherPass() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setMother(person.getPersonID(), mother.getPersonID());
        database.closeConnection(true);

        person = new PersonDAO(database.openConnection()).find(person.getPersonID());
        database.closeConnection(true);

        assertEquals(person.getMother(), mother.getPersonID());
    }

    @Test
    public void setMotherFail() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setMother("qazxswedc", mother.getPersonID());
        database.closeConnection(true);

        person = new PersonDAO(database.openConnection()).find(person.getPersonID());
        database.closeConnection(true);

        assertNotEquals(person.getMother(), mother.getPersonID());
    }

    @Test
    public void setSpousePass() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setSpouse(father.getPersonID(), mother.getPersonID());
        database.closeConnection(true);

        father = new PersonDAO(database.openConnection()).find(father.getPersonID());
        database.closeConnection(true);
        mother = new PersonDAO(database.openConnection()).find(mother.getPersonID());
        database.closeConnection(true);

        assertEquals(father.getSpouse(), mother.getPersonID());
        assertEquals(mother.getSpouse(), father.getPersonID());
    }

    @Test
    public void setSpouseFail() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).setSpouse("asdqwe", mother.getPersonID());
        database.closeConnection(true);

        father = new PersonDAO(database.openConnection()).find(father.getPersonID());
        database.closeConnection(true);
        mother = new PersonDAO(database.openConnection()).find(mother.getPersonID());
        database.closeConnection(true);

        assertNotEquals(father.getSpouse(), mother.getPersonID());
        assertNotEquals(mother.getSpouse(), father.getPersonID());
    }

    @Test
    public void findFamilyPass() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        List<Person> family = new PersonDAO(database.openConnection()).findFamily("Adam");
        database.closeConnection(true);

        assertTrue(family.size() > 1);
    }

    @Test
    public void findFamilyFail() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        List<Person> family = new PersonDAO(database.openConnection()).findFamily("qqazwed");
        database.closeConnection(true);

        assertNull(family);
    }

    @Test
    public void clearDataPass() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).clearData("Adam", "10101");
        database.closeConnection(true);

        List<Person> family = new PersonDAO(database.openConnection()).findFamily("Adam");
        database.closeConnection(true);

        assertTrue(family.size() == 1);
    }

    @Test
    public void clearDataFail() throws Exception
    {
        PersonDAO pDAO = new PersonDAO(database.openConnection());
        pDAO.insert(person);
        pDAO.insert(father);
        pDAO.insert(mother);
        database.closeConnection(true);

        new PersonDAO(database.openConnection()).clearData("Adam", "10000");
        database.closeConnection(true);

        List<Person> family = new PersonDAO(database.openConnection()).findFamily("Adam");
        database.closeConnection(true);

        assertNull(family);
    }
}

