package controllers.PersonControllers;

import controllers.Mediator.GameMediator;
import controllers.ObserveController.ObserverInterface;
import main.Utils;
import models.Factory.ChefFactory;
import models.Factory.CustomerFactory;
import models.Factory.WaiterFactory;
import models.Highscore;
import models.Restaurant;
import models.person.Chef;
import models.person.Customer;
import models.person.Person;
import models.person.Waiter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observer;
import java.util.Vector;

public class GameController implements ObserverInterface {
    public static GameController gameController = null;
    private final String fileName = "highscore.txt";
    public Restaurant restaurant;
    private boolean gameIsRunning;
    private Highscore highscore;

    public static GameController getGameController(String name){
        if(gameController == null) {
            gameController = new GameController(name);
        }
        return gameController;
    }

    private boolean isPaused;
    private int money;
    private int score;
    private Vector<Customer> customers;
    private Vector<Waiter> waiters;
    private Vector<Chef> chefs;
    private ChefFactory chefFactory;
    private CustomerFactory customerFactory;
    private WaiterFactory waiterFactory;
    private Utils util;
    private GameMediator gameMediator;

    private GameController(String name){
        this.gameMediator = new GameMediator();
        this.isPaused = true;
        this.chefFactory = new ChefFactory();
        this.customerFactory = new CustomerFactory();
        this.waiterFactory = new WaiterFactory();
        this.customers = new Vector<>();
        this.waiters = new Vector<>();
        this.chefs = new Vector<>();
        this.restaurant = Restaurant.getInstance(name);
        this.money = 1300;
        this.score = 0;
        this.util = new Utils();
        this.gameIsRunning = true;
        this.highscore = Highscore.getHighscore();
        firstInit();
    }

    private void firstInit(){
        for(int i = 0; i < 2; i++){
            chefs.add((Chef) chefFactory.createPerson(this.gameMediator));
            waiters.add((Waiter) waiterFactory.createPerson(this.gameMediator));
        }
    }

    synchronized public void deleteCustomer(Customer customer){
        customers.remove(customer);
        restaurant.setCurrentCustomerCount(customers.size());
    }



    public void logic(){
        addCustomer();
        scoreLogic();
    }

    public void scoreLogic(){

        for (Customer customer : customers) {
            if(customer.getTolerance() <= 0) {
                customers.remove(customer);
                restaurant.setCurrentCustomerCount(customers.size());
                this.score -= 300;
            }

        }
    }

    @Override
    synchronized public boolean receiveMessage() {
        return restaurant.getRestaurantInformation();
    }

    synchronized public void addCustomer(){
        if(receiveMessage()){ // kalau masi ada space coba random dan bikin baru .
            int random = util.getRandomInteger(4);
            if(random == 0) { // 25% chance of a new customer coming
                customers.add((Customer) customerFactory.createPerson(this.gameMediator));
                restaurant.setCurrentCustomerCount(customers.size());
            }
        }
    }

    public void endGameControl(){
        util.cls();
        highscore.endGameWrite(restaurant.getName(), this.score);
        highscore.showHighscoreAfterGame(restaurant.getName(), this.score);
        util.getEnter();
    }

    public void addNewChef(){
        chefs.add((Chef) chefFactory.createPerson(this.gameMediator));
    }

    public void addNewWaiter(){
        waiters.add((Waiter) waiterFactory.createPerson(this.gameMediator));
    }

    // ! getter setter


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public ChefFactory getChefFactory() {
        return chefFactory;
    }

    public void setChefFactory(ChefFactory chefFactory) {
        this.chefFactory = chefFactory;
    }

    public CustomerFactory getCustomerFactory() {
        return customerFactory;
    }

    public void setCustomerFactory(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    public WaiterFactory getWaiterFactory() {
        return waiterFactory;
    }

    public void setWaiterFactory(WaiterFactory waiterFactory) {
        this.waiterFactory = waiterFactory;
    }

    public Vector<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Vector<Customer> customers) {
        this.customers = customers;
    }

    public Vector<Waiter> getWaiters() {
        return waiters;
    }

    public void setWaiters(Vector<Waiter> waiters) {
        this.waiters = waiters;
    }

    public Vector<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(Vector<Chef> chefs) {
        this.chefs = chefs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isGameIsRunning() {
        return gameIsRunning;
    }

    public void setGameIsRunning(boolean gameIsRunning) {
        this.gameIsRunning = gameIsRunning;
    }
}
