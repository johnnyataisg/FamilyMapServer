package DataAccess;

import Models.Person;
import Models.User;

import java.sql.Connection;

/**
 * A class for reading and writing data in the User table in the database
 */
public class UserDAO
{
    /**
     * A database connection instance
     */
    private Connection connection;

    /**
     * Default constructor to create an empty UserDAO
     */
    public UserDAO()
    {
        //To be implemented
    }

    /**
     * Creates a UserDAO object and sets its database connection
     * @param conn A database connection object
     */
    public UserDAO(Connection conn)
    {
        //To be implemented
    }

    /**
     * Retrieves data for a user based upon a provided username from the database, else null if the username doesn't exist
     * @param username
     * @return
     */
    public User retrieveUser(String username)
    {
        //To be implemented
        return null;
    }

    /**
     * Inserts an user into the database on the premise that the username doesn't exist
     * @param user
     */
    public boolean insertUser(User user)
    {
        //To be implemented
        return true;
    }

    /**
     * Compares the username and password with database users and returns true if there is a match, else false
     * @param username
     * @param password
     * @return
     */
    public boolean isValidUser(String username, String password)
    {
        //To be implemented
        return true;
    }

    /**
     * Retrieves the person object for the given username if the username exists in the database, else null
     * @param username
     * @return
     */
    public Person retrievePerson(String username)
    {
        //To be implemented
        return null;
    }

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
