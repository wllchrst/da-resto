package models.State.WaiterState;

import models.State.StateInterface;
import models.person.Person;

public class WaiterFindChefState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new WaiterBringOrderState());
    }

    public void findChefToServe(Person person){
        person.changeState(new WaiterServingFoodState());
    }
}
