package models.State.ChefState;

import models.State.StateInterface;
import models.person.Person;

public class ChefCookState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new ChefDoneState());
    }
}
