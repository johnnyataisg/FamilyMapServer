package DataAccess;

import Models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO
{
    private Connection connection;

    public EventDAO() {}

    public EventDAO(Connection conn)
    {
        this.connection = conn;
    }

    public List<Event> retrieveRelatedEvents(String personID) throws DataAccessException
    {
        List<Event> eventList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE PersonID = ? ORDER BY Year ASC";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while (rs.next() == true)
            {
                Event event = new Event(rs.getString("EventID"),
                        rs.getString("Descendant"),
                        rs.getString("PersonID"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                eventList.add(event);
            }
            if (eventList.size() != 0)
            {
                return eventList;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error executing find command");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<Event> retrieveFamilyEvents(String username) throws DataAccessException
    {
        List<Event> eventList = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE Descendant = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next() == true)
            {
                Event event = new Event(rs.getString("EventID"),
                        rs.getString("Descendant"),
                        rs.getString("PersonID"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                eventList.add(event);
            }
            if (eventList.size() != 0)
            {
                return eventList;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error executing find command");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Event retrieveEvent(String eventID) throws DataAccessException
    {
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                event = new Event(rs.getString("EventID"),
                        rs.getString("Descendant"),
                        rs.getString("PersonID"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error executing find command");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Event retrieveBirthEvent(String personID) throws DataAccessException
    {
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE PersonID = ? AND EventType = 'Birth'";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                event = new Event(rs.getString("EventID"),
                        rs.getString("Descendant"),
                        rs.getString("PersonID"),
                        rs.getDouble("Latitude"),
                        rs.getDouble("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Error executing find command");
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public boolean insertEvent(Event event) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO Events " +
                "(" +
                "EventID, " +
                "Descendant, " +
                "PersonID, " +
                "Latitude, " +
                "Longitude, " +
                "Country, " +
                "City, " +
                "EventType, " +
                "Year" +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPerson());
            stmt.setString(4, String.valueOf(event.getLatitude()));
            stmt.setString(5, String.valueOf(event.getLongitude()));
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setString(9, String.valueOf(event.getYear()));
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing insert command");
        }
        return commit;
    }

    public boolean clearData(String username) throws DataAccessException
    {
        boolean commit = true;
        String sql = "DELETE FROM Events WHERE Descendant = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            commit = false;
            e.printStackTrace();
            throw new DataAccessException("Error executing delete command");
        }
        return commit;
    }
}
