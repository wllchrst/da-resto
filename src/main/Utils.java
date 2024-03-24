package main;

import models.Highscore;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Utils {
    Scanner scan;

    Random rand;

    public int getRandomInteger(int bound){
        return rand.nextInt(bound);
    }

    public Utils() {
        this.scan = new Scanner(System.in);
        this.rand = new Random();
    }

    public int getIntegerInput(){
        int input = scan.nextInt(); scan.nextLine();
        return input;
    }

    public String getStringInput(){
        String input = scan.nextLine();
        return input;
    }

    public boolean restaurantNameValidation(String input){
        if(input.length() > 15 || input.length() < 3)  {
            return false;
        }
        else {
            Highscore highscore = Highscore.getHighscore();
            Vector<String> names = highscore.getRestaurantNames();
            for(String name : names) {
                if(input.equals(name)) {
                    System.out.println("Name has been used");
                    return false;
                }
            }
        }
        return true;
    }

    public void sleep(int mili){
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getEnter(){
        System.out.println("press enter to continue");
        scan.nextLine();
    }

    public void cls(){
        for(int i = 0; i < 50; i++){
            System.out.println("");
        }
    }
}
