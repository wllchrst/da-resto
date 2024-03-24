package controllers.Mediator;

import models.State.ChefState.ChefCookState;
import models.State.ChefState.ChefDoneState;
import models.State.ChefState.ChefIdleState;
import models.State.CustomerState.CustomerEatState;
import models.State.CustomerState.CustomerWaitingWaiterState;
import models.State.WaiterState.WaiterBringOrderState;
import models.State.WaiterState.WaiterIdleState;
import models.State.WaiterState.WaiterFindChefState;
import models.State.WaiterState.WaiterServingFoodState;
import models.person.Chef;
import models.person.Customer;
import models.person.Waiter;

import java.util.Vector;

public class GameMediator {
    Vector<Customer> customers;
    Vector<Chef> chefs;
    Vector<Waiter> waiters;

    public GameMediator() {
        customers = new Vector<>();
        chefs = new Vector<>();
        waiters = new Vector<>();
    }

    synchronized public void registerCustomer(Customer customer){
        customers.add(customer);
    }

    synchronized public void registerWaiter(Waiter waiter){
        waiters.add(waiter);
    }

    synchronized public void registerChef(Chef chef){
        chefs.add(chef);
    }

    synchronized public void chefSendMessage(Customer customer, Waiter waiter, Chef chef) {
        chef.findingWaiter = true;
        while(chef.findingWaiter) {
            for(Waiter w : waiters){
                if(w.currentState instanceof WaiterIdleState) {
                    waiter.currentState = new WaiterServingFoodState();
                    waiter.setCurrentCustomer(chef.getCurrentCustomer());
                    chef.currentState.changeState(chef);
                    customer.currentState.changeState(customer);

                    chef.findingWaiter = false;
                    break;
                }
                else if(w.currentState instanceof WaiterFindChefState) {
                    exchangeState(waiter, chef);
                    waiter.findingChef = false;
                    chef.findingWaiter = false;
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    // waiter
    synchronized public void waiterSendMessage(Customer customer, Waiter waiter, Chef chef){
        if(waiter.currentState instanceof WaiterFindChefState) {
            waiterFindChef(customer, waiter);
        }
    }

    synchronized public void waiterFindChef(Customer customer, Waiter waiter){
        waiter.findingChef = true;
        while(waiter.findingChef){
            for(Chef chef : chefs) {
                if(chef.currentState instanceof ChefIdleState){
                    waiter.setCurrentChef(chef);
                    waiter.currentState.changeState(waiter);
                    // waiter state = wait chef cooking
                    waiter.getCurrentCustomer().setCurrentChef(chef);
                    customer.setCurrentChef(chef);
                    customer.currentState.changeState(customer);
                    // customer state -> customer wait food chef
                    chef.currentState.changeState(chef);
                    // chef state = chef cooking
                    chef.setCurrentCustomer(waiter.getCurrentCustomer());
                    chef.setCurrentWaiter(waiter);
                    waiter.findingChef = false;
                    chef.findingWaiter = false;
                    break;
                }
//                else if(chef.currentState instanceof ChefDoneState){
//                    exchangeState(waiter, chef);
//                    waiter.findingChef = false;
//                    chef.findingWaiter = false;
//                }
            }
        }
    }

    synchronized public void exchangeState(Waiter waiter, Chef chef){
        // penyimpen
        Customer chefCustomer = chef.getCurrentCustomer();
        Customer waiterCustomer = waiter.getCurrentCustomer();

        // bikin chef masak lagi
        chef.currentState = new ChefCookState();
        chef.setCurrentCustomer(waiterCustomer);
        waiterCustomer.currentState.changeState(waiterCustomer);

        // bikin waiter nganter makanan
        waiter.currentState = new WaiterServingFoodState();
        waiter.setCurrentCustomer(chefCustomer);
        chefCustomer.currentState.changeState(chefCustomer);
        chefCustomer.currentState = new CustomerEatState();
    }

    synchronized public void customerSendMessage(Customer sender, Waiter waiter, Chef chef){
        if(sender.currentState instanceof CustomerWaitingWaiterState) {
            customerWaitingWaiter(sender);
        }
   }

   synchronized public void customerWaitingWaiter(Customer customer){
       for(Waiter waiter : waiters){
           if(waiter.currentState instanceof WaiterIdleState) {
               // ada yang idle
               customer.currentState.changeState(customer);
               // customer state -> customer order state
               customer.setCurrentWaiter(waiter);
               waiter.currentState.changeState(waiter);
               // waiter state -> waiter take order state
               waiter.setCurrentCustomer(customer);
               break;
           }
       }
   }
}
