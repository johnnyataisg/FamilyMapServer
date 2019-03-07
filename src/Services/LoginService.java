package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Models.User;
import Requests.LoginRequest;
import Results.LoginResult;

public class LoginService
{
    public LoginService() {}

    public LoginResult login(LoginRequest request)
    {
        Database db = new Database();
        LoginResult result = null;
        try
        {
            UserDAO uDAO = new UserDAO(db.openConnection());
            boolean authSuccess = uDAO.authenticate(request.getUserName(), request.getPassword());
            db.closeConnection(true);

            if (authSuccess)
            {
                AuthTokenDAO aDAO = new AuthTokenDAO(db.openConnection());
                String tokenString = aDAO.insertUserAuth(request.getUserName());
                db.closeConnection(true);

                uDAO = new UserDAO(db.openConnection());
                User findUser = uDAO.find(request.getUserName());
                result = new LoginResult(tokenString, findUser.getUsername(), findUser.getPersonID());
                db.closeConnection(true);
            }
            else
            {
                db.openConnection();
                throw new DataAccessException("Invalid username or password");
            }
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result = new LoginResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                e2.printStackTrace();
                result = new LoginResult(e2.getMessage());
            }
        }
        return result;
    }
}
