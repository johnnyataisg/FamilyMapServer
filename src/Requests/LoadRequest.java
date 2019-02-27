package Requests;

import Models.Event;
import Models.Person;
import Models.User;

/**
 * A class for load requests
 */
public class LoadRequest
{
    /**
     * Array of user objects
     */
    private User[] users;

    /**
     * Array of person objects
     */
    private Person[] persons;

    /**
     * Array of event objects
     */
    private Event[] events;

    /**
     * Creates a LoadRequest object
     * @param userList
     * @param personList
     * @param eventList
     */
    public LoadRequest(User[] userList, Person[] personList, Event[] eventList)
    {

    }

    public User[] getUsers()
    {
        return null;
    }

    public Person[] getPersons()
    {
        return null;
    }

    public Event[] getEvents()
    {
        return null;
    }
}
