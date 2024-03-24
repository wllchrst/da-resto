package views;

import controllers.PersonControllers.GameController;
import main.ContinueGameFacade;
import main.PauseThread;
import main.Utils;
import models.Highscore;
import models.person.Chef;
import models.person.Customer;
import models.person.Waiter;

import java.util.Vector;

public class GameView extends Thread{
    GameController gameController;
    UpgradeMenu upgradeMenu;
    ContinueGameFacade continueGameFacade;
    PauseThread pauseThread;

    Utils util;

    public GameView(){
        util = new Utils();
        startingScreen();
    }

    public void highScore(){
        Highscore.getHighscore().showHighscore();
        util.getEnter();
        mainMenu();
    }


    public void newRestaurantMenu(){
        String restaurantName;
        do {
            System.out.print("Input Restaurant Name : ");
            restaurantName = util.getStringInput();
            if(util.restaurantNameValidation(restaurantName)) break;
        }while(true);

        this.gameController = GameController.getGameController(restaurantName);
        this.continueGameFacade = new ContinueGameFacade();
        this.upgradeMenu = new UpgradeMenu(this.gameController);
        continueGameFacade.pauseObjects(this.gameController);
        this.start();
    }


    @Override
    public synchronized void start() {
        this.pauseThread = new PauseThread(this.gameController, this.continueGameFacade);
        new Thread(this.pauseThread).start();
        while(gameController.isGameIsRunning()){
            if(gameController.isPaused()){
                pauseMenu();
            }
            util.sleep(1000);
            gameController.logic();
            mainGameView();
        }
    }

    public int highestTotal(){
        int customerLen = gameController.getCustomers().size();
        int chefLen = gameController.getChefs().size();
        int waiterLen = gameController.getWaiters().size();

        if(customerLen >= chefLen && customerLen >= waiterLen) return customerLen;
        else if(chefLen >= customerLen && chefLen >= waiterLen) return chefLen;
        else return waiterLen;
    }

    public void printPersons(){
        int maxLen = highestTotal();
        Vector<Customer> cs = gameController.getCustomers();
        Vector<Waiter> ws = gameController.getWaiters();
        Vector<Chef> chs = gameController.getChefs();
        for(int i = 0; i < maxLen; i++){
            String customer = "", waiter = "", chef = "";
            if(cs.size() > i)  {
                customer = cs.get(i).getMessage();
            }
            if(ws.size() > i) {
                waiter = String.format("%s %s", ws.get(i).getName(), ws.get(i).getMessage());
            }
            if(chs.size() > i){
                chef = String.format("%s %s", chs.get(i).getName(), chs.get(i).getMessage());
            }

            System.out.println(String.format("%-30s|%-30s|%-30s", customer, waiter, chef));
        }

    }


    public void mainGameView(){
        util.cls();
        System.out.println("Restaurant " + gameController.restaurant.getName() + " is on Business!");

        System.out.println("Status");
        System.out.println("Money : " + gameController.getMoney());
        System.out.println("Score : " + gameController.getScore());
        System.out.println("Size : " + gameController.restaurant.getRestaurantSize() + "\n");

        System.out.println("_".repeat(92));

        System.out.println(String.format("%-30s|%-30s|%-30s", "Customer", "Waiter", "Cook"));
        System.out.println("_".repeat(92));

        printPersons();
        System.out.println("_".repeat(92));

        System.out.println("Press Enter to go to pause menu");
    }


    public void pauseMenu(){
        util.cls();
        System.out.println("Pause Menu\n__________________________________________________________________");
        System.out.println("Status");
        System.out.println("Money : " + gameController.getMoney());
        System.out.println("Score : " + gameController.getScore());
        System.out.println("Size : " + gameController.restaurant.getRestaurantSize() + "\n");
        System.out.println("1. Continue Business");
        System.out.println("2. Upgrade Restaurant");
        System.out.println("3. Close Business");

        do {
            System.out.print("input [1..3] : ");
            int input = util.getIntegerInput();
            if(input == 1) {
                continueGameFacade.continueObjects(this.gameController);
                pauseThread.setPaused(false);
                break;
            }
            else if(input == 2){
                upgradeMenu.startingMenu();
                pauseMenu();
                break;
            }
            else if(input == 3){
                gameController.setGameIsRunning(false);
                continueGameFacade.pauseObjects(this.gameController);
                gameController.endGameControl();
                endGameView();
                break;
            }
        }while(true);
    }

    public void endGameView(){
        util.cls();
        System.out.println("Prove will emerge our spirit");
        util.getEnter();
        System.exit(0);
    }

    public void mainMenu(){
        util.cls();
        System.out.println("Main Menu\n");
        System.out.println("1. Play new restaurant");
        System.out.println("2. Highscore");
        System.out.println("3. Exit game");

        do {
            int input = util.getIntegerInput();
            if(input == 1) {
                newRestaurantMenu();
                break;
            }
            else if(input == 2){
                highScore();
                break;
            }
            else if(input == 3){
                return;
            }
            else continue;
        }while(true);

    }
    public void startingScreen(){
        System.out.println("\n\nDa Resto\n\n");
        util.getEnter();
        mainMenu();
    }

}
