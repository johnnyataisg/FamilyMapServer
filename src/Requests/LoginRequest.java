package Requests;

public class LoginRequest
{
    private String userName;
    private String password;

    public LoginRequest(String username, String pass)
    {
        this.userName = username;
        this.password = pass;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public String getPassword()
    {
        return this.password;
    }
}
