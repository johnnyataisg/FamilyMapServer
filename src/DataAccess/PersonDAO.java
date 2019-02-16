package DataAccess;

import Models.Person;

import java.sql.Connection;

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
    public Person retrievePerson(String personID)
    {
        //To be implemented
        return null;
    }

    /**
     * Looks up a username in the database and retrieves the person with that username
     * @param username
     * @return
     */
    public Person retrieveUser(String username)
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

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
