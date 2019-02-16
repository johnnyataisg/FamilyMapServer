package Models;
import java.sql.Connection;

/**
 * A class for a user
 */
public class User
{
    /**
     * Unique username of this user
     */
    private String username;
    /**
     * Password of this user
     */
    private String password;
    /**
     * Email of this user
     */
    private String email;
    /**
     * First name of this user
     */
    private String firstName;
    /**
     * Last name of this user
     */
    private String lastName;
    /**
     * Gender of this user
     */
    private char gender;
    /**
     * Unique ID for the person corresponding to this user
     */
    private String personID;

    /**
     * The single constructor for users
     * Sets this object's username to user and password to pass
     * @param user
     * @param pass
     */
    public User(String user, String pass)
    {
        //To be implemented later
    }

    public String getUsername()
    {
        //To be implemented later
        return "";
    }

    public String getPassword()
    {
        //To be implemented later
        return "";
    }

    public String getEmail()
    {
        //To be implemented later
        return "";
    }

    public String getFirstName()
    {
        //To be implemented later
        return "";
    }

    public String getLastName()
    {
        //To be implemented later
        return "";
    }

    public char getGender()
    {
        //To be implemented later
        return 'a';
    }

    public String getPersonID()
    {
        //To be implemented later
        return "";
    }

    public void setEmail()
    {
        //To be implemented later
    }

    public void setFirstName()
    {
        //To be implemented later
    }

    public void setLastName()
    {
        //To be implemented later
    }

    public void setGender()
    {
        //To be implemented later
    }

    public void setPeopleID()
    {
        //To be implemented later
    }
}
