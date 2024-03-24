package controllers.PersonControllers;

import models.person.Chef;

public class ChefController extends ControllerAbstract{
    private Chef chef;

    public ChefController(Chef chef){
        this.chef = chef;
    }

    public void chefIdling(){
        chef.setMessage("idle");
    }

    public void chefCooking(){
        String message = String.format("cooking <%s>", chef.getCurrentCustomer().getName());

        chef.setMessage(message);
        util.sleep(1000 * (6 - chef.getSpeed()));
        chef.currentState.changeState(chef);
        // chef state = done
        chef.notifyEveryPerson();
    }

    public void chefDoneCooking(){
        String message = String.format("done cooking");
        chef.setMessage(message);
        chef.notifyEveryPerson();

        // find waiter
    }
}
