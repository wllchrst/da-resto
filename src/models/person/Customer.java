package models.person;

import controllers.Mediator.GameMediator;
import controllers.PersonControllers.CustomerController;
import controllers.PersonControllers.GameController;
import models.State.CustomerState.*;
import models.State.StateInterface;

public class Customer extends Person{
     private int tolerance;
     private CustomerController customerController;
     private Waiter currentWaiter;
     private Chef currentChef;
     public Customer(String name, int tolerance, GameMediator gameMediator) {
          super(name, gameMediator);
          this.tolerance = tolerance;
          this.customerController = new CustomerController(this);
          currentState = new CustomerWaitingWaiterState();
          gameMediator.registerCustomer(this);
          this.currentWaiter = null;
          this.currentChef = null;
          // pascreated message everyone
          this.runThread();
     }



     @Override
     public void effects() {
//          System.out.println("STILL RUNNING");
          if(currentState instanceof CustomerWaitingWaiterState) {
               customerController.customerWaitingWaiter();
          }
          else if(currentState instanceof CustomerOrderState) {
               customerController.customerOrdering();
          }
          else if(currentState instanceof CustomerWaitFoodState) {
               customerController.customerWaitFood();
          }
          else if(currentState instanceof CustomerWaitFoodChefState) {
               customerController.customerWaitingFoodChef();
          }
          else if(currentState instanceof CustomerWaitFoodWaiterState){
               customerController.customerWaitingFoodWaiter();
          }
          else if(currentState instanceof CustomerEatState) {
               customerController.customerEating();
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
          gameMediator.customerSendMessage(this, currentWaiter, currentChef);
     }


     public CustomerController getCustomerController() {
          return customerController;
     }

     public void setCustomerController(CustomerController customerController) {
          this.customerController = customerController;
     }

     public Waiter getCurrentWaiter() {
          return currentWaiter;
     }

     public void setCurrentWaiter(Waiter currentWaiter) {
          this.currentWaiter = currentWaiter;
     }

     public Chef getCurrentChef() {
          return currentChef;
     }

     public void setCurrentChef(Chef currentChef) {
          this.currentChef = currentChef;
     }

     public int getTolerance() {
          return tolerance;
     }

     public void setTolerance(int tolerance) {
          this.tolerance = tolerance;
     }
}




