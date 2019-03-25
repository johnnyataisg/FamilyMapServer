package Results;

public class RegisterResult
{
    private String authToken;
    private String userName;
    private String personID;
    private String message;

    public RegisterResult(String token, String user, String perID)
    {
        this.authToken = token;
        this.userName = user;
        this.personID = perID;
    }

    public RegisterResult(String msg)
    {
        this.message = msg;
    }

     public String getToken()
     {
         return this.authToken;
     }

     public String getUsername()
     {
         return this.userName;
     }

     public String getPersonID()
     {
         return this.personID;
     }

     public String getMessage()
     {
         return this.message;
     }
}
