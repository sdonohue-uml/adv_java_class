package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.model.StockQuote;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

/**
 * Test class for unit tests of the StockQuote class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class StockQuoteTest {
    private StockQuote stockQuote;
    private LocalDateTime testDate; // 1/2/2020
    private BigDecimal testPrice;

    /**
     * Set up to initialize each test. Creates a StockQuote with hardcoded values.
     */
    @Before
    public void setup(){
        testDate = LocalDateTime.of(2020, Month.JANUARY, 2, 0 , 0);
        testPrice = new BigDecimal("12.34");
        stockQuote = new StockQuote("TEST", testPrice, testDate);
    }

    /**
     * Test that the initialized StockQuote returns the expected values.
     */
    @Test
    public void testInit(){
        assertEquals("Company Symbol should eqaul TEST", "TEST", stockQuote.getCompanySymbol());
        assertEquals("Price in Dollars should be 12.34", testPrice, stockQuote.getPrice());
        assertEquals("Date should be 1/2/2020", testDate, stockQuote.getDate());
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullCompany(){
        new StockQuote(null, testPrice, testDate);
    }

    /**
     * Test that an Exception is thrown if a negative value for price is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNegativePrice(){
        new StockQuote("TEST", new BigDecimal("-12.34"), testDate);
    }

    /**
     * Test that an Exception is thrown if a null date is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullDate(){
        new StockQuote("TEST", testPrice, null);
    }

    /**
     * Test that an Exception is thrown if a too large value for price is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullPrice(){
        new StockQuote("TEST", null, testDate);
    }
}
