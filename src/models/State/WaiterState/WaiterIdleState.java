package models.State.WaiterState;

import models.State.StateInterface;
import models.person.Person;

public class WaiterIdleState implements StateInterface {
    @Override
    public void changeState(Person person) {
        person.changeState(new WaiterTakeOrderState());
    }

    public void changeToServe(Person person){
        person.changeState(new WaiterServingFoodState());
    }

}
