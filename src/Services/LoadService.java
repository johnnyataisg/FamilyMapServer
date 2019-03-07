package Services;

import DataAccess.*;
import Models.Event;
import Models.Person;
import Models.User;
import Requests.LoadRequest;
import Results.LoadResult;

import java.util.List;

public class LoadService
{
    public LoadService() {}

    public LoadResult load(LoadRequest request)
    {
        List<User> userList = request.getUsers();
        List<Person> personList = request.getPersons();
        List<Event> eventList = request.getEvents();

        Database db = new Database();
        LoadResult result = null;
        try
        {
            new ClearService().clear();

            PersonDAO pDAO = new PersonDAO(db.openConnection());
            for (Person person : personList)
            {
                pDAO.insert(person);
            }
            db.closeConnection(true);

            UserDAO uDAO = new UserDAO(db.openConnection());
            for (User user : userList)
            {
                uDAO.insert(user);
            }
            db.closeConnection(true);

            EventDAO eDAO = new EventDAO(db.openConnection());
            for (Event event : eventList)
            {
                eDAO.insertEvent(event);
            }
            db.closeConnection(true);

            result = new LoadResult("Successfully added " + userList.size() + " users, " + personList.size() + " persons, and " + eventList.size() + " events to the database.");
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result = new LoadResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                e2.printStackTrace();
                result = new LoadResult(e2.getMessage());
            }
        }
        return result;
    }
}
