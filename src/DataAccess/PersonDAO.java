package DataAccess;

import Models.AuthToken;
import Models.Person;

import java.sql.Connection;
import java.util.List;

/**
 * A class for reading and writing the person table in the database
 */
public class PersonDAO
{
    /**
     * A database connection instance
     */
    private Connection connection;

    /**
     * Default constructor to create an empty PersonDAO
     */
    public PersonDAO()
    {
        //To be implemented
    }

    /**
     * Creates a PersonDAO and sets its database connection
     * @param conn
     */
    public PersonDAO(Connection conn)
    {
        //To be implemented
    }

    /**
     * Looks up a personID in the database and retrieves the person with that ID
     * @param personID
     * @return
     */
    public Person retrievePersonWithID(String personID)
    {
        //To be implemented
        return null;
    }

    /**
     * Looks up a username in the database and retrieves the person with that username
     * @param username
     * @return
     */
    public Person retrievePersonWithUsernmae(String username)
    {
        //To be implemented
        return null;
    }

    /**
     * Looks up the person with the current authentication token and retrieves all family members related to this person from the database
     * @param token
     * @return
     */
    public List<Person> retrieveFamilyMembers(AuthToken token)
    {
        //To be implemented
        return null;
    }

    /**
     * Retrieves all family members related to a person with a personID from the database
     * @param personID
     * @return
     */
    public List<Person> retrieveFamilyMembers(String personID)
    {
        //To be implemented
        return null;
    }

    /**
     * Inserts a person into the database if the personID doesn't exist, else return false
     * @param person
     * @return
     */
    public boolean insertPerson(Person person)
    {
        //To be implemented
        return true;
    }

    /**
     * Deletes a person with the given personID from the database
     * @param personID
     * @return
     */
    public boolean deletePerson(String personID)
    {
        //To be implemented
        return true;
    }

    /**
     * Delete all persons associated with an username if that username is valid, else return false
     * @param username
     * @return
     */
    public boolean deletePersons(String username)
    {
        //To be implemented
        return true;
    }

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
