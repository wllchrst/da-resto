package controllers.PersonControllers;

import models.person.Person;

import java.util.Vector;

public class AllPersonController {
    private Vector<Person> persons;

    public AllPersonController(){
        persons = new Vector<>();
    }

    public boolean isUnique(String name){
        for(Person p : persons)  {
            if(p.getName().equals(name)) return false;
        }
        return true;
    }

    public void insertNewPerson(Person person){
        persons.add(person);
    }

}
