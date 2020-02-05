package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for unit tests of the StockQuote class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class StockQuoteTest {
    private StockQuote stockQuote;

    /**
     * Set up to initialize each test. Creates a StockQuote with hardcoded values.
     */
    @Before
    public void setup(){
        stockQuote = new StockQuote("TEST", 12.34f);
    }

    /**
     * Test that the initialized StockQuote returns the expected values.
     */
    @Test
    public void testInit(){
        assertEquals("Company Symbol should eqaul TEST", "TEST", stockQuote.getCompanySymbol());
        assertEquals("Price in Dollars should be 12.34", "12.34", String.valueOf(stockQuote.getPriceInDollars()));
        assertEquals("Price in Cents should be 1234", 1234, stockQuote.getPriceInCents());
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullCompany(){
        new StockQuote(null, 12.34f);
    }

    /**
     * Test that an Exception is thrown if a negative value for price is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNegativePrice(){
        new StockQuote("TEST", -12.34f);
    }

    /**
     * Test that an Exception is thrown if a too large value for price is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testHugePrice(){
        new StockQuote("TEST", Float.MAX_VALUE);
    }
}
