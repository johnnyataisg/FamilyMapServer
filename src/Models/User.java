package Models;

import java.sql.Connection;

public class User
{
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID)
    {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public boolean equals(Object obj_2)
    {
        if (obj_2.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            User user_2 = (User)obj_2;

            if (!this.userName.equals(user_2.getUsername()))
            {
                return false;
            }
            if (!this.password.equals(user_2.getPassword()))
            {
                return false;
            }
            if (!this.email.equals(user_2.getEmail()))
            {
                return false;
            }
            if (!this.firstName.equals(user_2.getFirstName()))
            {
                return false;
            }
            if (!this.lastName.equals(user_2.getLastName()))
            {
                return false;
            }
            if (!this.gender.equals(user_2.getGender()))
            {
                return false;
            }
            if (!this.personID.equals(user_2.getPersonID()))
            {
                return false;
            }
            return true;
        }
    }

    public String getUsername()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getEmail()
    {
        return this.email;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getGender()
    {
        return this.gender;
    }

    public String getPersonID()
    {
        return this.personID;
    }
}
