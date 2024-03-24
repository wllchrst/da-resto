package controllers.PersonControllers;

import models.State.ChefState.ChefCookState;
import models.State.WaiterState.WaiterIdleState;
import models.person.Customer;
import models.person.Waiter;

public class WaiterController extends ControllerAbstract{

    private Waiter waiter;

    public WaiterController(Waiter waiter) {
        this.waiter = waiter;
    }

    public void waiterIdling(){
        waiter.setMessage("idle");
    }

    public void waiterTakingOrder() {
        String message = String.format("taking order <%s>", waiter.getCurrentCustomer().getName());
        waiter.setMessage(message);
        // waktu selsai pengambilan makanan
        util.sleep(1000 * (6 - waiter.getSpeed()));
        waiter.currentState.changeState(waiter);
        // waiter state -> waiter find chef state
        waiter.getCurrentCustomer().currentState.changeState(waiter.getCurrentCustomer());
        // customer state -> customer wait food chef
        waiter.notifyEveryPerson();
    }

    public void waiterBringingOrder(){
        if(waiter.getCurrentCustomer().getTolerance() <= 0) {
            waiter.currentState = new WaiterIdleState();
        }
        String message = String.format("bringing order <%s>", waiter.getCurrentCustomer().getName());
        waiter.setMessage(message);
        util.sleep(1000);
        waiter.currentState.changeState(waiter);
    }

    public void waiterServingFood(){
        String message = String.format("serving food <%s>", waiter.getCurrentCustomer().getName());
        waiter.setMessage(message);
        util.sleep(1000);
        waiter.currentState.changeState(waiter);
        Customer customer = waiter.getCurrentCustomer();
        customer.currentState.changeState(customer);

    }
}
