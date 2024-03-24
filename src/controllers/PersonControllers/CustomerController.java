package controllers.PersonControllers;

import main.Utils;
import models.Restaurant;
import models.person.Customer;

public class CustomerController {
    private Utils util;
    private Customer customer;
    private GameController gameController;
    public CustomerController(Customer customer){
        this.util = new Utils();
        this.customer = customer;
        this.gameController = GameController.getGameController("ngasal");
    }

    public void customerWaitingWaiter(){
        customer.setMessage(String.format("%s <%d> waiting waiter",customer.getName(), customer.getTolerance()));
        util.sleep(2000);
        customer.setTolerance(customer.getTolerance() - 2);
        customer.notifyEveryPerson();
    }


    public void customerOrdering(){
        String message = String.format("%s <%d> ordering<%s>", customer.getName(), customer.getTolerance(), customer.getCurrentWaiter().getName());
        customer.setMessage(message);
    }

    public void customerWaitFood(){
        String message = String.format("%s <%d> waiting food", customer.getName(), customer.getTolerance());
        customer.setMessage(message);
        util.sleep(4000);
        customer.setTolerance(customer.getTolerance() - 1);
    }

    public void customerWaitingFoodChef(){
        String message = String.format("%s <%d> waiting food <%s>", customer.getName(), customer.getTolerance(), customer.getCurrentChef().getName());
        customer.setMessage(message);
        util.sleep(4000);
        customer.setTolerance(customer.getTolerance() - 1);
    }

    public void customerWaitingFoodWaiter(){
        String message = String.format("%s <%d> waiting food <%s>", customer.getName(), customer.getTolerance(), customer.getCurrentWaiter().getName());
        customer.setMessage(message);
        util.sleep(4000);
        customer.setTolerance(customer.getTolerance() - 1);
    }


    public void customerEating(){
        customer.setMessage(String.format("%s eating", customer.getName()));
        util.sleep(6000);
        gameController.deleteCustomer(customer);
        int add = customer.getCurrentChef().getSkill() * 30;
        gameController.setMoney(gameController.getMoney() + add);
        gameController.setScore(gameController.getScore() + add);
    }
}