package Requests;

public class RegisterRequest
{
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    public RegisterRequest(String user, String pass, String email, String fName, String lName, String gen)
    {
        this.userName = user;
        this.password = pass;
        this.email = email;
        this.firstName = fName;
        this.lastName = lName;
        this.gender = gen;
    }

    public String getUserName()
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
}
