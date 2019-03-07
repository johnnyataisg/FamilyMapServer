package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Models.AuthToken;
import Models.Person;
import Results.PersonAllResult;

import java.util.List;

public class PersonAllService
{
    public PersonAllService() {}

    public PersonAllResult person(String token)
    {
        Database db = new Database();
        PersonAllResult result = null;
        try
        {
            AuthTokenDAO aDAO = new AuthTokenDAO(db.openConnection());
            AuthToken authToken = aDAO.retrieveAuthToken(token);
            if (authToken == null)
            {
                throw new DataAccessException("Invalid authentication token");
            }
            db.closeConnection(true);

            PersonDAO pDAO = new PersonDAO(db.openConnection());
            System.out.println("Searching for family members for the user: " + authToken.getUsername());
            List<Person> familyList = pDAO.findFamily(authToken.getUsername());
            if (familyList == null)
            {
                throw new DataAccessException("You don't have any family members in the database");
            }
            result = new PersonAllResult(familyList);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result = new PersonAllResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                e2.printStackTrace();
                result = new PersonAllResult(e2.getMessage());
            }
        }
        return result;
    }
}
