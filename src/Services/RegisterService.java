package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Models.Person;
import Models.User;
import Requests.RegisterRequest;
import Results.RegisterResult;
import java.util.UUID;

public class RegisterService
{
    public RegisterService() {}

    public RegisterResult register(RegisterRequest request)
    {
        Database db = new Database();
        RegisterResult result = null;
        try
        {
            //Insert the new user into the database
            UserDAO uDAO = new UserDAO(db.openConnection());
            User newUser = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), UUID.randomUUID().toString());
            uDAO.insert(newUser);
            db.closeConnection(true);

            //Find the new user in the database and return its credentials
            uDAO = new UserDAO(db.openConnection());
            User findUser = uDAO.find(request.getUserName());
            result = new RegisterResult(UUID.randomUUID().toString(), findUser.getUsername(), findUser.getPersonID());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result = new RegisterResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                e2.printStackTrace();
                result = new RegisterResult(e2.getMessage());
            }
        }
        return result;
    }
}
