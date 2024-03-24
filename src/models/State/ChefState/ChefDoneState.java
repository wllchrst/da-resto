package models.State.ChefState;

import models.State.StateInterface;
import models.person.Person;

public class ChefDoneState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new ChefIdleState());
    }
    public void doneToCooking(Person person){
        person.changeState(new ChefCookState());
    }
}
