import java.sql.Connection;

/**
 * The User model, with data members to each respective database field
 * <pre>
 *     <b>Domain</b>:
 *      username: String -- unique username for each user
 *      password: String -- password
 *      email: String -- Person's email
 *      firstName: String -- first name of person
 *      lastName: String -- last name of person
 *      gender: char -- either 'm' or 'f'
 *      personID: String -- foreign key from Person
 * </pre>
 */
public class User
{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;
    private String personID;

    /**
     * The single constructor for users
     * Takes the given unique username and creates a SQL statement to be run, returning other needed data values
     * @param userID the username for the user in the database
     * @param conn the established database connection object
     */
    public User(String userID, Connection conn)
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
}
