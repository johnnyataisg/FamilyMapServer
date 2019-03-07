package DataAccess;

import Models.User;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO
{
    private Connection connection;

    public UserDAO(Connection conn)
    {
        this.connection = conn;
    }

    public boolean insert(User user) throws DataAccessException
    {
        boolean commit = true;
        String sql = "INSERT INTO Users " +
                "(" +
                "Username, " +
                "Password, " +
                "Email, " +
                "Firstname, " +
                "Lastname, " +
                "Gender, " +
                "PersonID" +
                ") " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing insert command");
        }
        return commit;
    }

    public User find(String username) throws DataAccessException
    {
        User user = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                user = new User(rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("Firstname"),
                        rs.getString("Lastname"),
                        rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
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

    public boolean authenticate(String username, String password) throws DataAccessException
    {
        ResultSet rs = null;
        String sql = "SELECT Username, Password " +
                "FROM Users " +
                "WHERE Username = ? AND Password = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next() == true)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Error executing authentication command");
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
    }

    public boolean clearUsers() throws DataAccessException
    {
        boolean commit = true;

        String sql = "DELETE FROM Users";
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing clear Users command");
        }
        return commit;
    }

    public boolean deleteUser(String username) throws DataAccessException
    {
        boolean commit = true;

        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?";
        String sql2 = "DELETE FROM Users WHERE Username = ?";
        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next())
            {
                stmt = this.connection.prepareStatement(sql2);
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
            else
            {
                throw new SQLException();
            }
        }
        catch (SQLException e)
        {
            commit = false;
            throw new DataAccessException("Error executing delete command");
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
        return commit;
    }
}
