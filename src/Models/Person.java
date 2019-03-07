package Models;

public class Person
{
    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    public Person(String personID, String descendant, String firstName, String lastName, String gender, String father, String mother, String spouse)
    {
        this.personID = personID;
        this.descendant = descendant;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public String getPersonID()
    {
        return this.personID;
    }

    public String getDescendant()
    {
        return this.descendant;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getFather()
    {
        return this.father;
    }

    public String getMother()
    {
        return this.mother;
    }

    public String getSpouse()
    {
        return this.spouse;
    }

    public String getGender()
    {
        return this.gender;
    }

    public void setPersonID()
    {
        //To be implemented
    }

    public void setDescendant()
    {
        //To be implemented
    }

    public void setFirstName()
    {
        //To be implemented
    }

    public void setLastName()
    {
        //To be implemented
    }
    public void setGender()
    {
        //To be implemented
    }

    public void setFather()
    {
        //To be implemented
    }

    public void setMother()
    {
        //To be implemented
    }

    public void setSpouse()
    {
        //To be implemented
    }
}
