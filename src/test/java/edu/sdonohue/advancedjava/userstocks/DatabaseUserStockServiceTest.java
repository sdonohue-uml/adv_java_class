package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.model.Person;
import edu.sdonohue.advancedjava.service.userstocks.UserStockService;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceException;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceFactory;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabaseActivitiesService
 */
public class DatabaseUserStockServiceTest {

    private UserStockService userStockService;

    private void initDb() throws Exception {
        DatabaseUtils.initializeDatabase(null);
    }

    // do not assume db is correct
    @Before
    public void setUp() throws Exception {
        // we could also copy db state here for later restore before initializing
        initDb();
        userStockService = UserStockServiceFactory.getInstance();
    }

    // clean up after ourselves. (this could also restore db from initial state
    @After
    public void tearDown() throws Exception {
        initDb();
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Make sure activitiesService is available", userStockService);
    }

    @Test
    public void testGetPerson() throws UserStockServiceException {
        List<Person> personList = userStockService.getPerson();
        assertFalse("Make sure we get some Person objects from service", personList.isEmpty());
    }

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
        assertTrue("Found the person we added", found);
    }

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
        assertEquals("Verify the list has three stocks ", 3, stocks.size());

    }


}
