package edu.sdonohue.advancedjava;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for unit tests of the BasicStockService class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class BasicStockServiceTest {
    private BasicStockService basicStockService;

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        basicStockService = new BasicStockService();
    }

    /**
     * Tests that getQuote correctly returns a valid StockQuote.
     */
    @Test
    public void testGetQuote(){
        StockQuote stockQuote = basicStockService.getQuote("TEST");
        assertNotNull("Get Quote should not return null", stockQuote);
        assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNullCompany(){
        basicStockService.getQuote(null);
    }
}
