package Services;

import DataAccess.*;
import Models.Person;
import Models.User;
import Requests.RegisterRequest;
import Results.FillResult;
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
            //Insert the person object for the user into the database
            PersonDAO pDAO = new PersonDAO(db.openConnection());
            Person newPerson = new Person(UUID.randomUUID().toString(), request.getUserName(), request.getFirstName(), request.getLastName(), request.getGender(), null, null, null);
            pDAO.insert(newPerson);
            db.closeConnection(true);

            //Insert the new user into the database
            UserDAO uDAO = new UserDAO(db.openConnection());
            User newUser = new User(request.getUserName(), request.getPassword(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getGender(), newPerson.getPersonID());
            uDAO.insert(newUser);
            db.closeConnection(true);

            new FillService().fill(request.getUserName(), 4);

            //Create an authentication token for this user
            AuthTokenDAO aDAO = new AuthTokenDAO(db.openConnection());
            String tokenString = aDAO.insertUserAuth(newUser.getUsername());
            db.closeConnection(true);

            //Find the new user in the database and return its credentials
            uDAO = new UserDAO(db.openConnection());
            User findUser = uDAO.find(request.getUserName());
            result = new RegisterResult(tokenString, findUser.getUsername(), findUser.getPersonID());
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
