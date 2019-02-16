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
    private Connection cnonection;

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

    public void setConnection(Connection conn)
    {
        //To be implemented
    }
}
