package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.AuthToken;
import Models.Event;
import Results.EventAllResult;

import java.util.List;

public class EventAllService
{
    public EventAllService() {}

    public EventAllResult event(String token)
    {
        Database db = new Database();
        EventAllResult result = null;
        try
        {
            AuthTokenDAO aDAO = new AuthTokenDAO(db.openConnection());
            AuthToken authToken = aDAO.retrieveAuthToken(token);
            if (authToken == null)
            {
                throw new DataAccessException("Invalid authentication token");
            }
            db.closeConnection(true);

            EventDAO eDAO = new EventDAO(db.openConnection());
            List<Event> eventList = eDAO.retrieveFamilyEvents(authToken.getUsername());
            if (eventList == null)
            {
                throw new DataAccessException("You don't have any family events for your family in the database");
            }
            result = new EventAllResult(eventList);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            result = new EventAllResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                result = new EventAllResult(e2.getMessage());
            }
        }
        return result;
    }
}
