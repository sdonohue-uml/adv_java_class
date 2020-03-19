package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.model.Person;
import edu.sdonohue.advancedjava.service.userstocks.UserStockService;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceException;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceFactory;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabaseUserStockService
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class DatabaseUserStockServiceTest {

    private UserStockService userStockService;

    private void initDb() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(null);
    }

    /**
     * Setup to run before each unit test
     *
     * @throws DatabaseInitializationException
     */
    @Before
    public void setUp() throws DatabaseInitializationException {
        // we could also copy db state here for later restore before initializing
        initDb();
        userStockService = UserStockServiceFactory.getInstance();
    }

    /**
     * Resets the database after all unit tests
     *
     * @throws DatabaseInitializationException
     */
    @After
    public void tearDown() throws DatabaseInitializationException {
        initDb();
    }

    /**
     * Test that the UserStockService is available
     */
    @Test
    public void testGetInstance() {
        assertNotNull("UserStockService should be available", userStockService);
    }

    /**
     * Verify that getPerson() returns a populated list of person records
     *
     * @throws UserStockServiceException
     */
    @Test
    public void testGetPerson() throws UserStockServiceException {
        List<Person> personList = userStockService.getPerson();
        assertFalse("We should get a populated list of person objects", personList.isEmpty());
    }

    /**
     * Test that a newly added person is then returned by getPerson()
     *
     * @throws UserStockServiceException
     */
    @Test
    public void testAddOrUpdatePerson()throws UserStockServiceException {
        Person newPerson = PersonTest.createPerson();
        userStockService.addOrUpdatePerson(newPerson);
        List<Person> personList = userStockService.getPerson();
        boolean found = false;
        for (Person person : personList) {
            Timestamp returnedBirthDate = person.getBirthDate();
            Calendar returnCalendar = Calendar.getInstance();
            returnCalendar.setTimeInMillis(returnedBirthDate.getTime());
            if (returnCalendar.get(Calendar.MONTH) == PersonTest.birthDayCalendar.get(Calendar.MONTH)
                    &&
                    returnCalendar.get(Calendar.YEAR) == PersonTest.birthDayCalendar.get(Calendar.YEAR)
                    &&
                    returnCalendar.get(Calendar.DAY_OF_MONTH) == PersonTest.birthDayCalendar.get(Calendar.DAY_OF_MONTH)
                    &&
                    person.getLastName().equals(PersonTest.lastName)
                    &&
                    person.getFirstName().equals(PersonTest.firstName)) {
                found = true;
                break;
            }
        }
        assertTrue("We should be able to find the person we added", found);
    }

    /**
     * Verify that we get the correct list of stocks for a person
     *
     * @throws UserStockServiceException
     */
    @Test
    public void testGetStocksByPerson() throws UserStockServiceException {
        List<Person> personList = userStockService.getPerson();
        Person jones = null;
        for (Person person : personList){
            if (person.getLastName().toLowerCase().equals("jones")){
                jones = person;
                break;
            }
        }
        List<String> stocks = userStockService.getStocks(jones);
        assertEquals("Jones should be interested in three stocks", 3, stocks.size());
    }

    /**
     * Verify that we get the correct list of stocks for a person after adding a new stock.
     *
     * @throws UserStockServiceException
     */
    @Test
    public void testAddStocksToPerson() throws UserStockServiceException {
        List<Person> personList = userStockService.getPerson();
        Person jones = null;
        for (Person person : personList){
            if (person.getLastName().toLowerCase().equals("jones")){
                jones = person;
                break;
            }
        }
        userStockService.addStockToPerson("TEST", jones);
        List<String> stocks = userStockService.getStocks(jones);
        boolean found = false;
        for (String stock : stocks){
            if (stock.equals("TEST")){
                found = true;
                break;
            }
        }
        assertEquals("Jones should be interested in four stocks", 4, stocks.size());
        assertTrue("stock 'TEST' should be found in the list of stocks", found);
    }
}
