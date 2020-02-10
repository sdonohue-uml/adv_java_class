package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test class for unit tests of the StockQuote class.
 *
 * @author Sean Donohue
 * @version 1.1
 */
public class StockQuoteTest {
    private StockQuote stockQuote;
    private Date testDate;

    /**
     * Set up to initialize each test. Creates a StockQuote with hardcoded values.
     */
    @Before
    public void setup(){
        try {
            testDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/02/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stockQuote = new StockQuote("TEST", 12.34f, testDate);
    }

    /**
     * Test that the initialized StockQuote returns the expected values.
     */
    @Test
    public void testInit(){
        assertEquals("Company Symbol should eqaul TEST", "TEST", stockQuote.getCompanySymbol());
        assertEquals("Price in Dollars should be 12.34", "12.34", String.valueOf(stockQuote.getPriceInDollars()));
        assertEquals("Price in Cents should be 1234", 1234, stockQuote.getPriceInCents());
        assertEquals("Date should be today", testDate, stockQuote.getDate());
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullCompany(){
        new StockQuote(null, 12.34f, testDate);
    }

    /**
     * Test that an Exception is thrown if a negative value for price is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNegativePrice(){
        new StockQuote("TEST", -12.34f, testDate);
    }

    /**
     * Test that an Exception is thrown if a null date is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullDate(){
        new StockQuote("TEST", 12.34f, null);
    }

    /**
     * Test that an Exception is thrown if a too large value for price is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testHugePrice(){
        new StockQuote("TEST", Float.MAX_VALUE, testDate);
    }
}
