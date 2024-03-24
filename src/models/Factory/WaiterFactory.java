package models.Factory;

import controllers.Mediator.GameMediator;
import models.person.Person;
import models.person.Waiter;

public class WaiterFactory extends FactoryAbstract {
    @Override
    public Person createPerson(GameMediator gameMediator) {
        Waiter newWaiter = new Waiter(generateRandomName(), 1, gameMediator);
        pc.insertNewPerson(newWaiter);

        return newWaiter;
    }
}
