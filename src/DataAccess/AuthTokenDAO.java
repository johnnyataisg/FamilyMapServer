package DataAccess;

import Models.AuthToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthTokenDAO
{
    private Connection connection;

    public AuthTokenDAO() {}

    public AuthTokenDAO(Connection conn)
    {
        this.connection = conn;
    }

    public AuthToken retrieveAuthToken(String token) throws DataAccessException
    {
        AuthToken authToken = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Auth WHERE Token = ?";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next() == true)
            {
                authToken = new AuthToken(rs.getString("Token"), rs.getString("Username"));
                return authToken;
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

    public String insertUserAuth(String username) throws DataAccessException
    {
        String token = this.generateToken();
        String sql = "INSERT INTO Auth " +
                "(" +
                "Token, " +
                "Username" +
                ") " +
                "VALUES(?, ?)";

        try
        {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, token);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Error executing insert command");
        }
        return token;
    }

    private String generateToken()
    {
        return UUID.randomUUID().toString();
    }
}
