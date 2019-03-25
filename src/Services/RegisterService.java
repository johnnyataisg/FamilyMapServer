package Services;

import DataAccess.*;
import Models.Person;
import Models.User;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.FillResult;
import Results.LoginResult;
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
            UserDAO uDAO = new UserDAO(db.openConnection());
            User findUser = uDAO.find(request.getUserName());
            if (findUser != null)
            {
                throw new DataAccessException("Username already taken by another user");
            }
            db.closeConnection(true);

            //Insert the person object for the user into the database
            PersonDAO pDAO = new PersonDAO(db.openConnection());
            Person newPerson = new Person(UUID.randomUUID().toString(), request.getUserName(), request.getFirstName(), request.getLastName(), request.getGender(), null, null, null);
            pDAO.insert(newPerson);
            db.closeConnection(true);

            //Insert the new user into the database
            uDAO = new UserDAO(db.openConnection());
            User newUser = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), newPerson.getPersonID());
            uDAO.insert(newUser);
            db.closeConnection(true);

            new FillService().fill(request.getUserName(), 4);
            LoginResult loginResult = new LoginService().login(new LoginRequest(request.getUserName(), request.getPassword()));

            result = new RegisterResult(loginResult.getAuthToken(), loginResult.getUserName(), loginResult.getPersonID());
        }
        catch (DataAccessException e)
        {
            result = new RegisterResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                result = new RegisterResult(e2.getMessage());
            }
        }
        return result;
    }
}
