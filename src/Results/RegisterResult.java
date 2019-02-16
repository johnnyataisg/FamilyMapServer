package Results;

import Models.User;

/**
 * A class for a user registration result
 */
public class RegisterResult
{
    /**
     * The newly created user object
     */
    private User user;
    /**
     * The authentication token created for this new user
     */
    private String token;
    /**
     * The username of this new user
     */
    private String username;

    /**
     * Creates a new RegisterResult object
     * @param newUser
     * @param token
     * @param username
     */
    public RegisterResult(User newUser, String token, String username)
    {
    }


}
