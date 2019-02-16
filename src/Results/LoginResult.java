package Results;

import Models.User;

/**
 * A class for a user login result
 */
public class LoginResult
{
    /**
     * User's username
     */
    private String username;
    /**
     * User's authentication token
     */
    private String token;
    /**
     * User object with data
     */
    private User user;

    /**
     * Creates a login result object
     * @param user
     * @param username
     * @param token
     */
    public LoginResult(User user, String username, String token)
    {

    }
}
