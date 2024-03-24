package models.person;

import controllers.Mediator.GameMediator;
import models.State.StateInterface;

import java.util.Vector;

public abstract class Person implements Runnable{


    protected GameMediator gameMediator;
    protected String message;
    protected String name;
    protected boolean paused;
    public StateInterface currentState;


    public String getName() {
        return name;
    }

    public abstract void effects();

    public void setName(String name) {
        this.name = name;
    }


    public Person(String name, GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        this.paused = false;
        this.name = name;
    }

    public void runThread(){
        Thread thread = new Thread(this);
        thread.start();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void pauseThread(){
        while (this.paused) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void changeState(StateInterface state) {
        this.currentState = state;
    }

    public abstract void notifyEveryPerson();

    public boolean getPaused(){
        return this.paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    public StateInterface getCurrentState() {
        return currentState;
    }


}
