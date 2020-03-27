package edu.sdonohue.advancedjava.stocks;

import edu.sdonohue.advancedjava.service.stocks.DatabaseStockService;
import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockService;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the DatabaseStockService class.
 *
 * @author Sean Donohue
 */
public class DatabaseStockServiceTest {
    private DatabaseStockService databaseStockService;
    private LocalDateTime from; // 1/2/2020
    private LocalDateTime until; // 1/5/2020

    //inits the database
    private void initDb(){
        try {
            DatabaseUtils.initializeDatabase(null);
        } catch (DatabaseInitializationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        initDb();
        databaseStockService = new DatabaseStockService();
        from = LocalDateTime.of(2020, Month.JANUARY, 2, 0, 0, 0);
        until = LocalDateTime.of(2020, Month.JANUARY, 5, 0, 0, 0);
    }

    /**
     * Resets the database after each test.
     */
    @After
    public void tearDown(){
        initDb();
    }

    /**
     * Tests that getQuote correctly returns the right StockQuote.
     */
    @Test
    public void testGetQuote() throws StockServiceException {
        StockQuote stockQuote = databaseStockService.getQuote("GOOG");
        assertNotNull("Get Quote should not return null", stockQuote);
        assertEquals("GetQuote should return a StockQuote for the requested company",
                "GOOG", stockQuote.getCompanySymbol());
        assertEquals("GetQuote should return the most recent quote available",
                LocalDateTime.of(2015, 2, 3, 0, 0), stockQuote.getDate());
        assertEquals("Quote price should be $527", new BigDecimal("527"), stockQuote.getPrice());
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes in a date range.
     */
    @Test
    public void testGetQuotes() throws StockServiceException {
        List<StockQuote> stockQuotes = databaseStockService.getQuote("GOOG", from, until, StockService.IntervalEnum.DAILY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 4", 4, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should return a StockQuote for the requested company", "GOOG", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(from) && !stockQuote.getDate().isAfter(until);
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes for each hour within a date range.
     */
    @Test
    public void testGetQuotesHourly() throws StockServiceException {
        List<StockQuote> stockQuotes = databaseStockService.getQuote("GOOG", from, until, StockService.IntervalEnum.HOURLY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 73", 73, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should return a StockQuote for the requested company", "GOOG", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(from) && !stockQuote.getDate().isAfter(until);
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullCompany() throws StockServiceException {
        databaseStockService.getQuote(null);
    }

    /**
     * Test that an Exception is thrown if a non-existent company is passed.
     */
    @Test (expected = StockServiceException.class)
    public void testBadCompany() throws StockServiceException {
        databaseStockService.getQuote("NOTTHERE");
    }
}
