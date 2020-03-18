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
 * Unit test for UserStocks class
 */
public class UserStocksTest {

    @Before
    public void setup() {
        try {
            DatabaseUtils.initializeDatabase(null);
        } catch (DatabaseInitializationException e) {
            e.printStackTrace();
        }
    }

        /**
         * Testing helper method for generating UserStocks test data
         *
         * @return a UserStocks object that uses Person and UserStock
         * return from their respective create method.
         */
    public static UserStocks createUserStock() {
        Person person = PersonTest.createPerson();
        UserStocks userStocks = new UserStocks(person, "TEST");
        return userStocks;
    }

    @Test
    public void testPersonHobbiesGetterAndSetters() {
        Person person = PersonTest.createPerson();
        UserStocks userStocks = new UserStocks();
        int id = 10;
        userStocks.setId(id);
        userStocks.setPerson(person);
        userStocks.setStock("TEST");
        assertEquals("person matches", person, userStocks.getPerson());
        assertEquals("stock matches", "TEST", userStocks.getStock());
        assertEquals("id matches", id, userStocks.getId());
    }

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
        assertFalse("Different person", userStock1.equals(userStocks2));
    }

    @Test
    public void testEquals() {
        UserStocks UserStocks = createUserStock();
        assertTrue("Same objects are equal", UserStocks.equals(createUserStock()));
    }

    @Test
    public void testToString() {
        UserStocks UserStocks = createUserStock();
        assertTrue("toString has lastName", UserStocks.toString().contains(PersonTest.lastName));
        assertTrue("toString has firstName", UserStocks.toString().contains(PersonTest.firstName));
        assertTrue("toString has stock symbol", UserStocks.toString().contains("TEST"));
    }

}
