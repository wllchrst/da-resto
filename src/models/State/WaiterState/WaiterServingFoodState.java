package models.State.WaiterState;

import models.State.StateInterface;
import models.person.Person;

public class WaiterServingFoodState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new WaiterIdleState());
    }
}
