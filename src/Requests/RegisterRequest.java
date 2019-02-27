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
    private String userName;

    /**
     * User's proposed password
     */
    private String password;

    /**
     * User's email
     */
    private String email;

    /**
     * User's first name
     */
    private String firstName;

    /**
     * User's last name
     */
    private String lastName;

    /**
     * User's gender
     */
    private String gender;

    /**
     * Instantiate a RegisterRequest object with the new user's information
     * @param user
     * @param pass
     * @param email
     * @param fName
     * @param lName
     * @param gen
     */
    public RegisterRequest(String user, String pass, String email, String fName, String lName, String gen)
    {
        //To be implemented
    }
}
