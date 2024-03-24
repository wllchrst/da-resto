package main;

import controllers.PersonControllers.GameController;
import models.person.Chef;
import models.person.Customer;
import models.person.Waiter;

public class ContinueGameFacade {

    public void continueObjects(GameController gameController){
        gameController.setPaused(false);
        for(Customer c : gameController.getCustomers()){
            c.setPaused(false);
        }
        for(Waiter c : gameController.getWaiters()){
            c.setPaused(false);
        }
        for(Chef c : gameController.getChefs()){
            c.setPaused(false);
        }
    }

    public void pauseObjects(GameController gameController){
        gameController.setPaused(true);
        for(Customer c : gameController.getCustomers()){
            c.setPaused(true);
        }
        for(Waiter c : gameController.getWaiters()){
            c.setPaused(true);
        }
        for(Chef c : gameController.getChefs()){
            c.setPaused(true);
        }
    }
}
