package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.AuthToken;
import Models.Event;
import Results.EventRelatedResult;
import Results.EventResult;
import java.util.List;


public class EventRelatedService
{
    public EventRelatedService() {}

    public EventRelatedResult event(String personID, String token)
    {
        Database db = new Database();
        EventRelatedResult result = null;
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
            List<Event> events = eDAO.retrieveRelatedEvents(personID);
            if (events == null)
            {
                throw new DataAccessException("You don't have any family events for your family in the database");
            }
            result = new EventRelatedResult(events);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            result = new EventRelatedResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                result = new EventRelatedResult(e2.getMessage());
            }
        }
        return result;
    }
}
