package DataAccess;
import Models.Person;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO
{
    private Connection connection;

    public PersonDAO(Connection conn)
    {
        this.connection = conn;
    }

    public boolean insert(Person person) throws DataAccessException
    {
        boolean commit = true;

        String sql = "INSERT INTO Persons " +
                "(" +
                "PersonID, " +
                "Descendant, " +
                "Firstname, " +
                "Lastname, " +
                "Gender, " +
                "Father, " +
                "Mother, " +
                "Spouse" +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getDescendant());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFather());
            stmt.setString(7, person.getMother());
            stmt.setString(8, person.getSpouse());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing insert command");
        }
        return commit;
    }

    public Person find(String personID) throws DataAccessException
    {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                person = new Person(rs.getString("PersonID"),
                        rs.getString("Descendant"),
                        rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getString("Gender"),
                        rs.getString("Father"),
                        rs.getString("Mother"),
                        rs.getString("Spouse"));
                return person;
            }
        }
        catch (SQLException e)
        {
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

    public boolean clearPersons() throws DataAccessException
    {
        boolean commit = true;

        String sql = "DELETE FROM Persons";
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing clear Persons command");
        }
        return commit;
    }

    public int size() throws DataAccessException
    {
        String sql = "SELECT COUNT(*) FROM Persons";
        ResultSet rs = null;

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            int result = rs.getInt("COUNT(*)");
            return result;
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Error executing size command");
        }
    }
}
