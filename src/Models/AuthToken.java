package Models;

public class AuthToken
{
    private String token;
    private String username;

    public AuthToken(String token, String username)
    {
        this.token = token;
        this.username = username;
    }

    public String getToken()
    {
        return this.token;
    }

    public String getUsername()
    {
        return this.username;
    }
}
