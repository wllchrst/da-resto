package models.person;

import controllers.Mediator.GameMediator;
import controllers.PersonControllers.ChefController;
import models.State.ChefState.ChefCookState;
import models.State.ChefState.ChefDoneState;
import models.State.ChefState.ChefIdleState;
import models.State.StateInterface;

public class Chef extends Person{
    public boolean findingWaiter;
    private int speed;
    private ChefController chefController;
    private int skill;
    private Customer currentCustomer;
    private Waiter currentWaiter;
    public Chef(String name, int skill, int speed, GameMediator gameMediator) {
        super(name, gameMediator);
        findingWaiter = false;
        currentState = new ChefIdleState();
        this.skill = skill;
        this.speed = speed;
        gameMediator.registerChef(this);
        this.currentCustomer = null;
        this.chefController = new ChefController(this);
        this.runThread();
    }

    @Override
    public void effects() {
        if(currentState instanceof ChefIdleState){
            chefController.chefIdling();
        }
        else if(currentState instanceof ChefCookState) {
            chefController.chefCooking();
        }
        else if(currentState instanceof ChefDoneState) {
            chefController.chefDoneCooking();
        }
    }

    @Override
    public void run() {
        while(true){
            if(this.paused){
                pauseThread();
            }
            effects();
        }
    }

    @Override
    public void notifyEveryPerson() {
        gameMediator.chefSendMessage(currentCustomer, currentWaiter, this);
    }


    public Waiter getCurrentWaiter() {
        return currentWaiter;
    }

    public void setCurrentWaiter(Waiter currentWaiter) {
        this.currentWaiter = currentWaiter;
    }

    public ChefController getChefController() {
        return chefController;
    }

    public void setChefController(ChefController chefController) {
        this.chefController = chefController;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
