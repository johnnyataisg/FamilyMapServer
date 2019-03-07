package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Models.Person;
import Results.PersonResult;

public class PersonService
{
    public PersonService() {}

    public PersonResult person(String personID)
    {
        Database db = new Database();
        PersonResult result = null;
        try
        {
            PersonDAO pDAO = new PersonDAO(db.openConnection());
            Person person = pDAO.find(personID);
            result = new PersonResult(person.getDescendant(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFather(), person.getMother(), person.getSpouse());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {

        }


    }
}
