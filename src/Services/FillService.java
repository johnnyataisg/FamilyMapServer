package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import Models.*;
import Results.FillResult;
import com.google.gson.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class FillService
{
    private MaleNames mNames;
    private FemaleNames fNames;
    private Surnames sNames;

    public FillService()
    {
        try
        {
            Scanner scanner = new Scanner(new File("src/JSON/mnames.json"));
            StringBuilder str = new StringBuilder();
            while (scanner.hasNext())
            {
                str.append(scanner.next());
            }
            this.mNames = new Gson().fromJson(str.toString(), MaleNames.class);

            scanner = new Scanner(new File("src/JSON/fnames.json"));
            str = new StringBuilder();
            while (scanner.hasNext())
            {
                str.append(scanner.next());
            }
            this.fNames = new Gson().fromJson(str.toString(), FemaleNames.class);

            scanner = new Scanner(new File("src/JSON/snames.json"));
            str = new StringBuilder();
            while (scanner.hasNext())
            {
                str.append(scanner.next());
            }
            this.sNames = new Gson().fromJson(str.toString(), Surnames.class);
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong there");
        }
    }

    public FillResult fill(String username, int generations)
    {
        Database db = new Database();
        FillResult result = null;
        try
        {
            User user = new UserDAO(db.openConnection()).find(username);
            if (user == null)
            {
                throw new DataAccessException("The user does not exist");
            }
            db.closeConnection(true);

            PersonDAO pDAO = new PersonDAO(db.openConnection());
            pDAO.clearData(username, user.getPersonID());
            Person base = pDAO.find(user.getPersonID());
            createParents(username, base, pDAO, generations);

            result = new FillResult("Successfully added " + ((int)Math.pow(2, generations + 1) - 1) + " persons and Y events to the database");
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            result = new FillResult(e.getMessage());
            try
            {
                db.closeConnection(false);
            }
            catch (DataAccessException e2)
            {
                e2.printStackTrace();
                result = new FillResult(e2.getMessage());
            }
        }
        return result;
    }

    private void createParents(String username, Person child, PersonDAO pDAO, int generation) throws DataAccessException
    {
        if (generation == 0)
        {
            return;
        }

        Person father = new Person(UUID.randomUUID().toString(), username, this.randomMaleName(), this.randomSurname(), "m", null, null, null);
        Person mother = new Person(UUID.randomUUID().toString(), username, this.randomFemaleName(), this.randomSurname(), "f", null, null, null);
        pDAO.insert(father);
        pDAO.insert(mother);
        pDAO.setFather(child.getPersonID(), father.getPersonID());
        pDAO.setMother(child.getPersonID(), mother.getPersonID());
        pDAO.setSpouse(father.getPersonID(), mother.getPersonID());

        createParents(username, father, pDAO, generation - 1);
        createParents(username, mother, pDAO, generation - 1);
    }

    private String randomMaleName()
    {
        int size = this.mNames.getData().size();
        int index = new Random().nextInt(size);

        return this.mNames.getData().get(index);
    }

    private String randomFemaleName()
    {
        int size = this.fNames.getData().size();
        int index = new Random().nextInt(size);

        return this.fNames.getData().get(index);
    }

    private String randomSurname()
    {
        int size = this.sNames.getData().size();
        int index = new Random().nextInt(size);

        return this.sNames.getData().get(index);
    }
}
