package models;

import controllers.ObserveController.SubjectInterface;

public class Restaurant implements SubjectInterface {
    private static Restaurant restaurant = null;
    private int restaurantSize;
    private int currentCustomerCount;
    private String name;


    private Restaurant(String name) {
        this.currentCustomerCount = 0;
        this.restaurantSize = 4;
        this.name = name;
    }

    public static Restaurant getInstance(String name){
        if(restaurant == null){
            synchronized (Restaurant.class) {
                if(restaurant == null) {
                    restaurant = new Restaurant(name);
                }
            }
        }
        return restaurant;
    }

    @Override
    synchronized public boolean getRestaurantInformation() {
        // masi ada space buat customer baru.
        return currentCustomerCount < restaurantSize;
    }

    synchronized public int getCurrentCustomerCount() {
        return currentCustomerCount;
    }

    synchronized public void setCurrentCustomerCount(int currentCustomerCount) {
        this.currentCustomerCount = currentCustomerCount;
    }

    public int getRestaurantSize() {
        return restaurantSize;
    }

    public void setRestaurantSize(int restaurantSize) {
        this.restaurantSize = restaurantSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
