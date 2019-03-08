package Services;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Models.AuthToken;
import Models.Event;
import Results.EventResult;

public class EventService
{
    public EventService() {}

    public EventResult event(String eventID, String token)
    {
        Database db = new Database();
        EventResult result = null;
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
            Event event = eDAO.retrieveEvent(eventID);
            if (event == null)
            {
                throw new DataAccessException("Invalid eventID parameter");
            }
            if (!event.getDescendant().equals(authToken.getUsername()))
            {
                throw new DataAccessException("Requested event does not belong to this user");
            }
            result = new EventResult(event.getDescendant(), event.getEventID(), event.getPerson(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            result = new EventResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                result = new EventResult(e2.getMessage());
            }
        }
        return result;
    }
}
