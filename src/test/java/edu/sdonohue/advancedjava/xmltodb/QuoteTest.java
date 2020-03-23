package edu.sdonohue.advancedjava.xmltodb;

import edu.sdonohue.advancedjava.model.Quote;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the Quote class.
 *
 * @author Sean Donohue
 */
public class QuoteTest {

    private Quote quote;

    /**
     * Setup to run before each test.
     */
    @Before
    public void setup(){
        quote = new Quote("TEST", "100", "2015-02-10 00:00:01");
    }

    /**
     * Test that all the getters are behaving properly.
     */
    @Test
    public void testGetters(){
        assertEquals("Symbol should be 'TEST'", "TEST", quote.getSymbol());
        assertEquals("Price should be 100", new BigDecimal(100), quote.getPrice());
        assertEquals("Time should be 2015-02-10 00:00:01",
                Timestamp.valueOf("2015-02-10 00:00:01"), quote.getTime());
        assertEquals("ID should be 0 for uncommitted Quotes", 0, quote.getId());
    }

    /**
     * Test that all the setters are behaving properly.
     */
    @Test
    public void testSetters(){
        quote.setSymbol("NEW");
        quote.setPrice(new BigDecimal(10));
        quote.setTime(Timestamp.valueOf("2015-02-11 00:00:01"));
        assertEquals("Symbol should be 'NEW'", "NEW", quote.getSymbol());
        assertEquals("Price should be 10", new BigDecimal(10), quote.getPrice());
        assertEquals("Time should be 2015-02-11 00:00:01",
                Timestamp.valueOf("2015-02-11 00:00:01"), quote.getTime());
    }
}
