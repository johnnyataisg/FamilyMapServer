package Models;

import java.sql.Connection;

public class User
{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
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

    public String getGender()
    {
        //To be implemented later
        return "";
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
