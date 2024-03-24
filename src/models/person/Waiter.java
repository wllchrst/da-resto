package models.person;

import controllers.Mediator.GameMediator;
import controllers.PersonControllers.WaiterController;
import models.State.WaiterState.*;

public class Waiter extends Person{
    public boolean findingChef;
    public boolean waiterWaitingChef;
    private WaiterController waiterController;
    private int speed;
    private Customer currentCustomer;
    private Chef currentChef;

    public Waiter(String name, int speed, GameMediator gameMediator) {
        super(name, gameMediator);
        findingChef = false;
        this.speed = speed;
        currentState = new WaiterIdleState();
        gameMediator.registerWaiter(this);
        this.waiterController = new WaiterController(this);
        this.currentCustomer = null;
        this.currentChef = null;
        this.waiterWaitingChef = true;
        this.runThread();
    }

    @Override
    public void run() {
        while(true){
            if(this.paused) {
                pauseThread();
            }
            effects();
        }
    }

    @Override
    public void effects() {
        if(currentState instanceof WaiterIdleState) {
            waiterController.waiterIdling();
        }
        else if(currentState instanceof WaiterTakeOrderState) {
            waiterController.waiterTakingOrder();
        }
        else if(currentState instanceof WaiterBringOrderState) {
            waiterController.waiterBringingOrder();
        }
        else if(currentState instanceof WaiterServingFoodState) {
            waiterController.waiterServingFood();
        }
    }

    @Override
    public void notifyEveryPerson() {
        gameMediator.waiterSendMessage(currentCustomer, this, currentChef);
    }



    public boolean isWaiterWaitingChef() {
        return waiterWaitingChef;
    }

    public void setWaiterWaitingChef(boolean waiterWaitingChef) {
        this.waiterWaitingChef = waiterWaitingChef;
    }

    public Chef getCurrentChef() {
        return currentChef;
    }

    public void setCurrentChef(Chef currentChef) {
        this.currentChef = currentChef;
    }

    public WaiterController getWaiterController() {
        return waiterController;
    }

    public void setWaiterController(WaiterController waiterController) {
        this.waiterController = waiterController;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
