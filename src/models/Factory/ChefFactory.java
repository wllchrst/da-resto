package models.Factory;

import controllers.Mediator.GameMediator;
import models.person.Chef;
import models.person.Person;

public class ChefFactory extends FactoryAbstract {
    @Override
    public Person createPerson(GameMediator gameMediator) {
        Chef newChef = new Chef(generateRandomName(), 1, 1, gameMediator);
        pc.insertNewPerson(newChef);

        return newChef;
    }
}
