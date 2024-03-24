package views;

import controllers.PersonControllers.GameController;
import main.Utils;
import models.Restaurant;
import models.person.Chef;
import models.person.Waiter;

import java.util.Vector;

public class UpgradeMenu {
    private GameController gameController;
    private Utils util;

    public UpgradeMenu(GameController gameController) {
        this.gameController = gameController;
        util = new Utils();
    }

    public void increaseRestaurantSeat(){
        Restaurant restaurant = gameController.getRestaurant();
        int currentMoney = gameController.getMoney();
        int price = restaurant.getRestaurantSize() * 100;

        if(paymentController(currentMoney, price)) restaurant.setRestaurantSize(restaurant.getRestaurantSize() + 1);
    }

    public void hireNewEmployee(){
        printStatus();

        System.out.println("HIRE NEW EMPLOYEE");
        System.out.printf("1. Hire New Waiter <Rp. %d>\n", 150 * gameController.getWaiters().size());
        System.out.printf("2. Hire New Chef <Rp. %d>\n", 200 * gameController.getChefs().size());
        System.out.println("3. Back to upgrade menu");

        do {
            System.out.print("Input [1..3] : ");
            int input = util.getIntegerInput();
            if(input == 1){
                hireWaiter();
                break;
            }
            else if(input == 2){
                hireChef();
                break;
            }
            else if(input == 3){
                startingMenu();
                break;
            }
        } while(true);
    }

    public void upgradeWaiter(){
        Vector<Waiter> waiters = gameController.getWaiters();

        System.out.printf("%-10s |%-10s |%-10s\n", "No.", "Initial", "Speed");
        System.out.println("-". repeat(35));
        int index = 1;
        for(Waiter waiter : waiters){
            System.out.printf("%-10d |%-10s |%-10d\n", index++,waiter.getName(), waiter.getSpeed());
        }
        System.out.println("-". repeat(35));
        int input;
        String type;
        do {
            System.out.print("Input waiter's number to upgrade [0 to exit] : ");
            input = util.getIntegerInput();

            if(input == 0){
                startingMenu();
                break;
            }

            input--;
            if(input >= waiters.size()) {
                System.out.println("out of index");
                util.getEnter();
                startingMenu();
                break;
            }
            Waiter currentWaiter = waiters.get(input);

            currentWaiter.setSpeed(currentWaiter.getSpeed() + 1);

            util.getEnter();
            startingMenu();
            break;
        }while(true);
    }

    public void upgradeCook(){
        Vector<Chef> chefs= gameController.getChefs();

        System.out.printf("%-10s |%-10s |%-10s |%-10s\n", "No.", "Initial", "Speed", "Skill");
        System.out.println("-". repeat(50));
        int index = 1;
        for(Chef chef : chefs){
            System.out.printf("%-10d |%-10s |%-10d |%-10d \n", index++, chef.getName(), chef.getSpeed(), chef.getSkill());
        }
        System.out.println("-". repeat(50));
        int input;
        String type;
        do {
            System.out.printf("Input chef's number to upgrade [0 to exit] : ");
            input = util.getIntegerInput();

            if(input == 0) {
                startingMenu();
                break;
            }
            input--;
            if(input >= chefs.size()) {
                System.out.println("out of index");
                util.getEnter();
                startingMenu();
                break;
            }
            System.out.print("Input Upgrade Type [Skill | Speed] : ");
            Chef currentChef = chefs.get(input);
            type = util.getStringInput();
            if(type.equals("Skill")) {
                if(currentChef.getSkill() >= 5) {
                    System.out.println("Chef's maximum skill is 5");
                    util.getEnter();
                    startingMenu();
                    break;
                }

                currentChef.setSkill(currentChef.getSkill() + 1);
                System.out.println("Success");
                util.getEnter();
                startingMenu();
                break;
            }
            else if(type.equals("Speed")) {
                if(currentChef.getSpeed() >= 5){
                    System.out.println("Chef's maximum speed is 5");
                    util.getEnter();
                    startingMenu();
                    break;
                }
                currentChef.setSpeed(currentChef.getSpeed() + 1);
                System.out.println("Success");
                util.getEnter();
                startingMenu();
                break;
            }
            else continue;
        } while(true);
    }

    public void hireWaiter(){
        int currentMoney = gameController.getMoney();
        int price = gameController.getWaiters().size() * 150;

        if(paymentController(currentMoney, price)) gameController.addNewWaiter();
        else System.out.println("Your money is not enough to hire new Waiter");

        util.getEnter();
        startingMenu();
        return;
    }

    public void hireChef(){
        int currentMoney = gameController.getMoney();
        int price = gameController.getChefs().size() * 200;

        if(paymentController(currentMoney, price)) gameController.addNewChef();
        else  System.out.println("Your money is not enough to hire new chef");

        util.getEnter();
        startingMenu();
        return;
    }

    public boolean paymentController(int currentMoney, int price){
        if(currentMoney >= price){
            gameController.setMoney(currentMoney - price);
            return true;
        }
        else return false;
    }

    public void printStatus(){
        util.cls();
        System.out.println("Status");
        System.out.printf("Money : Rp. %d%n", gameController.getMoney());
        System.out.printf("Score : Rp. %d Points%n", gameController.getScore());
        System.out.printf("Size : %d Seats\n%n", gameController.getRestaurant().getRestaurantSize());
    }


    public void startingMenu(){
        printStatus();

        System.out.printf("1. Increase Restaurant's Seat <Rp. %d>\n", gameController.getRestaurant().getRestaurantSize() * 100);
        System.out.println("2. Hire new Employee");
        System.out.println("3. Upgrade Waiter <Rp. 150>");
        System.out.println("4. Upgrade Cook <Rp. 150>");
        System.out.println("5. Back To Pause Menu");

        int input;
        do {
            System.out.print("Input [1..5] : \n");
            input = util.getIntegerInput();
            if(input == 1) {
                increaseRestaurantSeat();
                break;
            }
            else if(input == 2) {
                hireNewEmployee();
                break;
            }
            else if(input == 3) {
                upgradeWaiter();
                break;
            }
            else if(input == 4) {
                upgradeCook();
                break;
            }
            else if(input == 5) {
                return;
            }
        } while(true);

    }


    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
