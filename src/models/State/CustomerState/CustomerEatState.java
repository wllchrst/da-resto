package models.State.CustomerState;

import models.State.StateInterface;
import models.person.Person;

public class CustomerEatState implements StateInterface {
    @Override
    public void changeState(Person person) {
        // keluar dari restoran
    }
}
