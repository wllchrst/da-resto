package models.Factory;

import controllers.Mediator.GameMediator;
import controllers.PersonControllers.AllPersonController;
import models.person.Person;

import java.util.Random;

public abstract class FactoryAbstract {
    private Random rand;
    protected AllPersonController pc;

    public FactoryAbstract() {
        this.rand = new Random();
        this.pc = new AllPersonController();
    }

    public abstract Person createPerson(GameMediator gameMediator);

    public String generateRandomName(){
        String name = "";
        do {
            int firstAdder = rand.nextInt(26);
            int secondAdder = rand.nextInt(26);
            name = String.format("%c%c", 'A' + firstAdder, 'A' + secondAdder);

            if(pc.isUnique(name)) break;
        }while (true);
        return name;
    }
}
