package Results;

public class LoginResult
{
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public LoginResult(String token, String user, String perID)
    {
        this.authToken = token;
        this.userName = user;
        this.personID = perID;
    }

    public LoginResult(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return this.message;
    }
}
