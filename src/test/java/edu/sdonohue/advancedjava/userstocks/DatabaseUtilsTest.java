package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.util.DatabaseConnectionException;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *  Unit tests for the DatabaseUtils class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class DatabaseUtilsTest {

    /**
     * Test initializing the database with the default script doesn't throw an exception.
     *
     * @throws DatabaseInitializationException
     */
    @Test
    public void testGoodInitFile() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(null);
    }

    /**
     * Test that initializing with a non-existent script throws an exception.
     *
     * @throws DatabaseInitializationException
     */
    @Test(expected = DatabaseInitializationException.class)
    public void testBadInitFile() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase("bogus");
    }

    /**
     * Tests that we can get a connection to the database.
     *
     * @throws DatabaseConnectionException
     */
    @Test
    public void testGetConnection() throws DatabaseConnectionException {
        Connection connection = DatabaseUtils.getConnection();
        assertNotNull("We should be able to get a connection to the db", connection);
    }

    /**
     * Tests that we can execute statements against the database.
     *
     * @throws DatabaseConnectionException
     * @throws SQLException
     */
    @Test
    public void testGetConnectionWorks() throws DatabaseConnectionException, SQLException {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from person");
        assertTrue("We should be able to execute a statement", execute);
    }
}
