package models.State.CustomerState;

import models.State.StateInterface;
import models.person.Person;

public class CustomerWaitFoodChefState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new CustomerWaitFoodWaiterState());
    }
}
