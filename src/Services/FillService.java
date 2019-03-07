package Services;

import DataAccess.*;
import Models.*;
import Results.FillResult;
import com.google.gson.*;
import java.io.File;
import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class FillService
{
    private MaleNames mNames;
    private FemaleNames fNames;
    private Surnames sNames;
    private Locations locations;
    private int peopleAdded;
    private int eventsAdded;

    public FillService()
    {
        this.peopleAdded = 0;
        this.eventsAdded = 0;
        try
        {
            Scanner scanner = new Scanner(new File("src/JSON/mnames.json"));
            StringBuilder str = new StringBuilder();
            while (scanner.hasNextLine())
            {
                str.append(scanner.nextLine());
            }
            this.mNames = new Gson().fromJson(str.toString(), MaleNames.class);

            scanner = new Scanner(new File("src/JSON/fnames.json"));
            str = new StringBuilder();
            while (scanner.hasNextLine())
            {
                str.append(scanner.nextLine());
            }
            this.fNames = new Gson().fromJson(str.toString(), FemaleNames.class);

            scanner = new Scanner(new File("src/JSON/snames.json"));
            str = new StringBuilder();
            while (scanner.hasNextLine())
            {
                str.append(scanner.nextLine());
            }
            this.sNames = new Gson().fromJson(str.toString(), Surnames.class);

            scanner = new Scanner(new File("src/JSON/locations.json"));
            str = new StringBuilder();
            while (scanner.hasNextLine())
            {
                str.append(scanner.nextLine());
            }
            this.locations = new Gson().fromJson(str.toString(), Locations.class);
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
                throw new DataAccessException("Invalid username or generations parameter");
            }
            db.closeConnection(true);

            //Find user's person object and delete all other associated data from the Persons table
            PersonDAO pDAO = new PersonDAO(db.openConnection());
            pDAO.clearData(username, user.getPersonID());
            Person base = pDAO.find(user.getPersonID());
            db.closeConnection(true);

            //Clear events table of all data and insert new birth event for new user
            EventDAO eDAO = new EventDAO(db.openConnection());
            eDAO.clearData(username);
            Place place = this.randomLocation();
            eDAO.insertEvent(new Event(UUID.randomUUID().toString(), username, base.getPersonID(), place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Birth", new Random().nextInt(28) + 1990));
            db.closeConnection(true);

            Connection connection = db.openConnection();
            createParents(username, base, connection, generations);
            result = new FillResult("Successfully added " + this.peopleAdded + " persons and " + this.eventsAdded + " events to the database");
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

    private void createParents(String username, Person child, Connection conn, int generation) throws DataAccessException
    {
        if (generation == 0)
        {
            return;
        }

        Person father = new Person(UUID.randomUUID().toString(), username, this.randomMaleName(), this.randomSurname(), "m", null, null, null);
        Person mother = new Person(UUID.randomUUID().toString(), username, this.randomFemaleName(), this.randomSurname(), "f", null, null, null);
        PersonDAO pDAO = new PersonDAO(conn);
        pDAO.insert(father);
        pDAO.insert(mother);
        this.peopleAdded += 2;
        pDAO.setFather(child.getPersonID(), father.getPersonID());
        pDAO.setMother(child.getPersonID(), mother.getPersonID());
        pDAO.setSpouse(father.getPersonID(), mother.getPersonID());

        createEvents(child.getPersonID(), father.getPersonID(), mother.getPersonID(), conn, username);

        createParents(username, father, conn, generation - 1);
        createParents(username, mother, conn, generation - 1);
    }

    private void createEvents(String childID, String fatherID, String motherID, Connection conn, String descendant) throws DataAccessException
    {
        EventDAO eDAO = new EventDAO(conn);
        Event childBirth = eDAO.retrieveBirthEvent(childID);

        Place place = this.randomLocation();
        int husbandBirthYear = new Random().nextInt(27) + (childBirth.getYear() - 45);
        Event birth = new Event(UUID.randomUUID().toString(), descendant, fatherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Birth", husbandBirthYear);
        eDAO.insertEvent(birth);
        this.eventsAdded++;

        place = this.randomLocation();
        int wifeBirthYear = new Random().nextInt(10) - 5 + husbandBirthYear;
        birth = new Event(UUID.randomUUID().toString(), descendant, motherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Birth", wifeBirthYear);
        eDAO.insertEvent(birth);
        this.eventsAdded++;

        place = this.randomLocation();
        int youngerYear = Math.min(husbandBirthYear, wifeBirthYear);
        int marriageYear = new Random().nextInt(5) + youngerYear + 18;
        eDAO.insertEvent(new Event(UUID.randomUUID().toString(), descendant, fatherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Marriage", marriageYear));
        this.eventsAdded++;
        eDAO.insertEvent(new Event(UUID.randomUUID().toString(), descendant, motherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Marriage", marriageYear));
        this.eventsAdded++;

        if (2019 - husbandBirthYear >= 100)
        {
            place = this.randomLocation();
            int husbandDeathYear = new Random().nextInt(10) + husbandBirthYear + 100;
            eDAO.insertEvent(new Event(UUID.randomUUID().toString(), descendant, fatherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Death", husbandDeathYear));
            this.eventsAdded++;
        }

        if (2019 - wifeBirthYear >= 100)
        {
            place = this.randomLocation();
            int wifeDeathYear = new Random().nextInt(10) + wifeBirthYear + 100;
            eDAO.insertEvent(new Event(UUID.randomUUID().toString(), descendant, motherID, place.getLatitude(), place.getLongitude(), place.getCountry(), place.getCity(), "Death", wifeDeathYear));
            this.eventsAdded++;
        }
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

    private Place randomLocation()
    {
        int size = this.locations.getData().size();
        int index = new Random().nextInt(size);

        return this.locations.getData().get(index);
    }
}
