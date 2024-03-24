package models.State;

import models.person.Person;

public interface StateInterface {
    boolean isCurrentState = true;

    public void changeState(Person person);


}
