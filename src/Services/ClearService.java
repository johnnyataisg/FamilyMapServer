package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Results.ClearResult;

public class ClearService
{
    public ClearService() {}

    public ClearResult clear()
    {
        Database db = new Database();
        ClearResult result = null;
        try
        {
            //Delete all data from all tables in the database
            db.openConnection();
            db.clearTables();
            result = new ClearResult("Clear succeeded.");
        }
        catch (DataAccessException e)
        {
            result = new ClearResult(e.getMessage());
        }
        return result;
    }
}
