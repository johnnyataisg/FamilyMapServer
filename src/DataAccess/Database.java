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
            //This is how we set up the driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException
    {
        try
        {
            //The Structure for this Connection is driver:language:path
            //The pathing assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:src/db/familymap.sqlite";

            // Open a database connection to the file given in the path
            this.conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            this.conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return this.conn;
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e)
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
                    "Password TEXT NOT NULL, " +
                    "Email TEXT, " +
                    "Firstname TEXT NOT NULL, " +
                    "Lastname TEXT NOT NULL, " +
                    "Gender TEXT, " +
                    "PersonID TEXT NOT NULL, " +
                    "PRIMARY KEY (Username), " +
                    "FOREIGN KEY (PersonID) REFERENCES Persons(PersonID)" +
                    ")";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Persons " +
                    "(" +
                    "PersonID TEXT NOT NULL UNIQUE, " +
                    "Descendant TEXT NOT NULL, " +
                    "Firstname TEXT NOT NULL, " +
                    "Lastname TEXT NOT NULL, " +
                    "Gender TEXT, " +
                    "Father TEXT, " +
                    "Mother TEXT, " +
                    "Spouse TEXT, " +
                    "PRIMARY KEY (PersonID), " +
                    "FOREIGN KEY (Descendant) REFERENCES Users(Username)" +
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
        openConnection();

        try
        {
            Statement stmt = conn.createStatement();

            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);

            closeConnection(true);
        }
        catch (DataAccessException e)
        {
            closeConnection(false);
            throw e;
        }
        catch (SQLException e)
        {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
