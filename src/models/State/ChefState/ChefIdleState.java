package models.State.ChefState;

import models.State.StateInterface;
import models.person.Person;

public class ChefIdleState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new ChefCookState());
    }
}
