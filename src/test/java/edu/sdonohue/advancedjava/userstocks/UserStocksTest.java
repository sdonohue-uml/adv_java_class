package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.model.Person;
import edu.sdonohue.advancedjava.model.UserStocks;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for UserStocks class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class UserStocksTest {

    /**
     * Setup to run before each unit test.
     */
    @Before
    public void setup() {
        try {
            DatabaseUtils.initializeDatabase(null);
        } catch (DatabaseInitializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing helper method for generating UserStocks test data.
     *
     * @return a UserStocks object that uses Person and UserStock
     * return from their respective create method.
     */
    public static UserStocks createUserStock() {
        Person person = PersonTest.createPerson();
        UserStocks userStocks = new UserStocks(person, "TEST");
        return userStocks;
    }

    /**
     * Test that getters and setters for user stocks work correctly.
     */
    @Test
    public void testPersonHobbiesGetterAndSetters() {
        Person person = PersonTest.createPerson();
        UserStocks userStocks = new UserStocks();
        int id = 10;
        userStocks.setId(id);
        userStocks.setPerson(person);
        userStocks.setStock("TEST");
        assertEquals("Person should be the same object as created", person, userStocks.getPerson());
        assertEquals("Stock symbol should be 'TEST'", "TEST", userStocks.getStock());
        assertEquals("Id should be 10", id, userStocks.getId());
    }

    /**
     * Verify that UserStocks with different Persons are not equal.
     */
    @Test
    public void testEqualsNegativeDifferentPerson() {
        UserStocks userStock1 = createUserStock();
        userStock1.setId(10);
        Person person = new Person();
        Timestamp birthDate = new Timestamp(PersonTest.birthDayCalendar.getTimeInMillis() + 10000);
        person.setBirthDate(birthDate);
        person.setFirstName(PersonTest.firstName);
        person.setLastName(PersonTest.lastName);
        UserStocks userStocks2 = new UserStocks(person, "TEST");
        assertFalse("Person records should not be equal", userStock1.equals(userStocks2));
    }

    /**
     * Test that UserStocks with the same data are equal.
     */
    @Test
    public void testEquals() {
        UserStocks UserStocks = createUserStock();
        assertTrue("Identical UserStocks should be equal", UserStocks.equals(createUserStock()));
    }

    /**
     * Test that toString() returns correctly.
     */
    @Test
    public void testToString() {
        UserStocks UserStocks = createUserStock();
        assertTrue("toString should contain 'Marks'", UserStocks.toString().contains(PersonTest.lastName));
        assertTrue("toString should contain 'Spencer'", UserStocks.toString().contains(PersonTest.firstName));
        assertTrue("toString should contain 'TEST'", UserStocks.toString().contains("TEST"));
    }

}
