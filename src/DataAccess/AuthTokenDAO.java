package DataAccess;

import Models.AuthToken;
import Models.User;

import java.sql.Connection;
import java.util.List;

/**
 * A class for reading and writing in the AuthToken table in the database
 */
public class AuthTokenDAO
{
    /**
     * A database connection instance
     */
    private Connection connection;

    /**
     * Default constructor to create an empty AuthTokenDAO
     */
    public AuthTokenDAO()
    {
        //To be implemented
    }

    /**
     * Creates an AuthTokenDAO object and sets its database connection
     * @param conn
     */
    public AuthTokenDAO(Connection conn)
    {
        //To be implemented
    }

    /**
     * Looks up an token string in the database and retrieves the user with that token string
     * @param token
     * @return
     */
    public User retrieveUser(String token)
    {
        //To be implemented
        return null;
    }

    /**
     * Looks up an username in the database and retrieves the AuthTokens with that username
     * @param username
     * @return
     */
    public List<AuthToken> retrieveTokens(String username)
    {
        //To be implemented
        return null;
    }

    /**
     * Creates a new authentication token for a username and saves it in the database
     * @param username
     */
    public void insertUserAuth(String username)
    {
        //To be implemented
    }

    /**
     * Private hashing algorithm to generate an unique authentication token string
     * @return
     */
    private String generateToken()
    {
        //To be implemented
        return null;
    }

    /**
     * Deletes an authentication token from the database
     * @param token
     */
    public boolean deleteToken(String token)
    {
        //To be implemented
        return true;
    }

    /**
     * Clears the database of all authentication tokens related to an username
     * @param username
     */
    public boolean deleteUserSessions(String username)
    {
        //To be implemented
        return true;
    }

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
