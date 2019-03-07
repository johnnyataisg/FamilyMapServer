package Requests;

import Models.Event;
import Models.Person;
import Models.User;
import java.util.List;

public class LoadRequest
{
    private List<User> users;

    private List<Person> persons;

    private List<Event> events;

    public LoadRequest(List<User> userList, List<Person> personList, List<Event> eventList)
    {
        this.users = userList;
        this.persons = personList;
        this.events = eventList;
    }

    public List<User> getUsers()
    {
        return this.users;
    }

    public List<Person> getPersons()
    {
        return this.persons;
    }

    public List<Event> getEvents()
    {
        return this.events;
    }
}
