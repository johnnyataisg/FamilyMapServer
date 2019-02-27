package Results;

import Models.User;

/**
 * A class for a user registration result
 */
public class RegisterResult
{
    /**
     * The authentication token created for this new user
     */
    private String authToken;

    /**
     * The username of this new user
     */
    private String userName;

    /**
     * The Person ID of the user's generated Person object
     */
    private String personID;

    /**
     * Create a success response with the new user's information and authentication token
     * @param token
     * @param user
     * @param perID
     */
    public RegisterResult(String token, String user, String perID)
    {
    }

     public String getToken()
     {
         return null;
     }

     public String getUsername()
     {
         return null;
     }

     public String getPersonID()
     {
         return null;
     }
}
