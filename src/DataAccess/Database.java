package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database
{
    private Connection conn;

    static
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DataAccessException
    {
        try
        {
            final String CONNECTION_URL = "jdbc:sqlite:src/db/familymap.sqlite";

            this.conn = DriverManager.getConnection(CONNECTION_URL);

            this.conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return this.conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.conn.commit();
            } else {
                this.conn.rollback();
            }

            this.conn.close();
            this.conn = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException
    {
        this.openConnection();

        try
        {
            Statement stmt = this.conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS Users " +
                    "(" +
                    "Username TEXT NOT NULL UNIQUE, " +
                    "Password TEXT, " +
                    "Email TEXT, " +
                    "Firstname TEXT, " +
                    "Lastname TEXT, " +
                    "Gender TEXT, " +
                    "PersonID TEXT, " +
                    "PRIMARY KEY (Username), " +
                    "FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)" +
                    ")";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Persons " +
                    "(" +
                    "PersonID TEXT NOT NULL UNIQUE, " +
                    "Descendant TEXT, " +
                    "Firstname TEXT, " +
                    "Lastname TEXT, " +
                    "Gender TEXT, " +
                    "Father TEXT, " +
                    "Mother TEXT, " +
                    "Spouse TEXT, " +
                    "PRIMARY KEY (PersonID)" +
                    ")";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Events " +
                    "(" +
                    "EventID TEXT NOT NULL UNIQUE, " +
                    "Descendant TEXT, " +
                    "PersonID TEXT, " +
                    "Latitude REAL, " +
                    "Longtitude REAL, " +
                    "Country TEXT, " +
                    "City TEXT, " +
                    "EventType TEXT, " +
                    "Year INTEGER, " +
                    "PRIMARY KEY (EventID), " +
                    "FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)" +
                    ")";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Auth " +
                    "(" +
                    "Token TEXT NOT NULL UNIQUE, " +
                    "Username TEXT, " +
                    "PRIMARY KEY (Token), " +
                    "FOREIGN KEY (Username) REFERENCES Users(Username)" +
                    ")";
            stmt.executeUpdate(sql);

            this.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e)
        {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while creating tables");
        }
    }

    public void clearTables() throws DataAccessException
    {
        this.openConnection();
        try
        {
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Auth";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

            closeConnection(true);
        }
        catch (DataAccessException e)
        {
            closeConnection(false);
            throw new DataAccessException("Some error occurred");
        }
        catch (SQLException e)
        {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
