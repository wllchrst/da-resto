package models.State.CustomerState;

import models.State.StateInterface;
import models.person.Person;

public class CustomerWaitFoodState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new CustomerWaitFoodChefState());
    }
}
