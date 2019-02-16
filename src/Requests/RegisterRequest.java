package Requests;

import Models.User;

/**
 * A class that contains data of new users requesting registration in the database
 */
public class RegisterRequest
{
    /**
     * User's proposed unique username
     */
    private String username;

    /**
     * User's proposed email address link
     */
    private String email;

    /**
     * Creates a register request object with data within a User model
     * @param user
     */
    public RegisterRequest(User user)
    {

    }
}
