package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.database.DatabaseStockService;
import edu.sdonohue.advancedjava.stocks.StockQuote;
import edu.sdonohue.advancedjava.stocks.StockService;
import edu.sdonohue.advancedjava.stocks.StockServiceException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseStockServiceTest {
    private DatabaseStockService databaseStockService;
    private Calendar from; // 1/2/2010
    private Calendar until; // 1/5/2020

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        databaseStockService = new DatabaseStockService();
        from = Calendar.getInstance();
        from.clear();
        from.set(2020, Calendar.JANUARY, 2);
        until = Calendar.getInstance();
        until.clear();
        until.set(2020, Calendar.JANUARY, 5);
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
        List<StockQuote> stockQuotes = databaseStockService.getQuote("AMZN", from, until, StockService.IntervalEnum.DAILY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 4", 4, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "AMZN", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(asLocalDateTime(from)) && !stockQuote.getDate().isAfter(asLocalDateTime(until));
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes for each hour within a date range.
     */
    @Test
    public void testGetQuotesHourly() throws StockServiceException {
        List<StockQuote> stockQuotes = databaseStockService.getQuote("AMZN", from, until, StockService.IntervalEnum.HOURLY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 73", 73, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should return a StockQuote for the requested company", "AMZN", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(asLocalDateTime(from)) && !stockQuote.getDate().isAfter(asLocalDateTime(until));
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

    //Converts a Calendar to a LocalDateTime
    private LocalDateTime asLocalDateTime(Calendar calendar)
    {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
