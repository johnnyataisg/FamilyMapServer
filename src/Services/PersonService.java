package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Models.AuthToken;
import Models.Person;
import Results.PersonResult;

public class PersonService
{
    public PersonService() {}

    public PersonResult person(String personID, String token)
    {
        Database db = new Database();
        PersonResult result = null;
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
            Person person = pDAO.find(personID);
            if (person == null)
            {
                throw new DataAccessException("Invalid personID parameter");
            }
            if (!person.getDescendant().equals(authToken.getUsername()))
            {
                throw new DataAccessException("Requested person does not belong to this user");
            }
            result = new PersonResult(person.getDescendant(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFather(), person.getMother(), person.getSpouse());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            result = new PersonResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                result = new PersonResult(e2.getMessage());
            }
        }
        return result;
    }
}
