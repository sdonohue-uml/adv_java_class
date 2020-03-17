package edu.sdonohue.advancedjava.service;

import edu.sdonohue.advancedjava.model.Person;

import java.util.List;

/**
 *
 */
public interface UserStockService {

    /**
     * Get a list of all people
     *
     * @return a list of Person instances
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<Person> getPerson() throws UserStockServiceException;

    /**
     * Add a new person or update an existing Person's data
     *
     * @param person a person object to either update or create
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    void addOrUpdatePerson(Person person) throws UserStockServiceException;

    /**
     * Get a list of all a person's stock interests.
     *
     * @return a list of stock symbols
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    List<String> getStocks(Person person) throws UserStockServiceException;

    /**
     * Assign a hobby to a person.
     *
     * @param stock  The stock symbol to assign
     * @param person The person to assign the stock too.
     * @throws UserStockServiceException if a service can not read or write the requested data
     *                                    or otherwise perform the requested operation.
     */
    public void addStockToPerson(String stock, Person person) throws UserStockServiceException;

}
