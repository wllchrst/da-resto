package main;

import controllers.PersonControllers.GameController;

import java.util.Scanner;

public class PauseThread implements Runnable{

    Scanner scanner;
    private boolean isPaused;
    GameController gameController;
    ContinueGameFacade continueGameFacade;

    public PauseThread(GameController gameController, ContinueGameFacade continueGameFacade){
        scanner = new Scanner(System.in);
        this.isPaused = true;
        this.gameController = gameController;
        this.continueGameFacade = continueGameFacade;
    }

    @Override
    public void run() {
        while(gameController.isGameIsRunning()) {
            if(!isPaused) {
                scanner.nextLine();
                pausingController();
            }
        }
    }


    public void pausingController(){

        if(isPaused) {
            isPaused = false;
            continueGameFacade.continueObjects(this.gameController);
        }
        else if(!isPaused) {
            isPaused = true;
            continueGameFacade.pauseObjects(this.gameController);
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
