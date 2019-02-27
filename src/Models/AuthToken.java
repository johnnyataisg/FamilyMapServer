package Models;
import java.sql.Connection;

/**
 * A class representing an authentication token
 */
public class AuthToken
{
    /**
     * Unique authentication token string for this user
     */
    private String token;
    /**
     * Username of the user
     */
    private String username;

    /**
     * Take an username and run a query in the database to find the authentication token, else create a new token
     * @param userID
     */
    public AuthToken(String userID)
    {
        //To be implemented later
    }

    /**
     * Sets this object's token and username to a string user and string tokenString
     * @param user
     * @param tokenString
     */
    public AuthToken(String user, String tokenString)
    {
        //To be implemented later
    }

    public String getToken()
    {
        //To be implemented later
        return "";
    }

    public String getUsername()
    {
        //To be implemented later
        return "";
    }
}
