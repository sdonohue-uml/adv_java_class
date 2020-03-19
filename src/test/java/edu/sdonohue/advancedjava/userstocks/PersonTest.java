package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.model.Person;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Person class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class PersonTest {

    /**
     * 1/12/1990 - Calendar birthdate for Person test.
     */
    public static final Calendar birthDayCalendar = Calendar.getInstance();

    static {
        birthDayCalendar.set(1990, Calendar.JANUARY, 12);
    }

    /**
     * "Spencer" - first name for Person test.
     */
    public static final String firstName = "Spencer";

    /**
     * "Marks" - last name for Person test.
     */
    public  static final String lastName = "Marks";

    /**
     * 1/12/1990 - Timestamp birthdate for Person test.
     */
    public static final Timestamp birthDate = new Timestamp(birthDayCalendar.getTimeInMillis());

    /**
     * Setup to run before each unit test.
     */
    @Before
    public void setup(){
        try {
            DatabaseUtils.initializeDatabase(null);
        } catch (DatabaseInitializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing helper method for generating Person test data
     *
     * @return a Person object that uses static constants for data.
     */
    public static Person createPerson() {
        Person person = new Person();
        person.setBirthDate(birthDate);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }

    /**
     * Verify that getters and setters for person are working.
     */
    @Test
    public void testPersonGettersAndSetters() {
        Person person = createPerson();
        int id = 10;
        person.setId(id);
        assertEquals("first name should be Spencer", firstName, person.getFirstName());
        assertEquals("last name should be Marks", lastName, person.getLastName());
        assertEquals("birthday should be 1/12/1990", birthDate, person.getBirthDate());
        assertEquals("id should be 10", id, person.getId());
    }

}
