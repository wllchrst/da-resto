package models.Factory;

import controllers.Mediator.GameMediator;
import models.person.Customer;
import models.person.Person;

public class CustomerFactory extends FactoryAbstract{
    @Override
    public Person createPerson(GameMediator gameMediator) {
        Customer newCustomer = new Customer(generateRandomName(), 20, gameMediator);
        pc.insertNewPerson(newCustomer);
        return newCustomer;
    }
}



