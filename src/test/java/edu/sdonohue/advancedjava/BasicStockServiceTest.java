package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.StockService.IntervalEnum;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for unit tests of the BasicStockService class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class BasicStockServiceTest {
    private BasicStockService basicStockService;
    private Calendar from; // 1/2/2020
    private Calendar until; // 1/5/2020

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        basicStockService = new BasicStockService();
        from = Calendar.getInstance();
        from.clear();
        from.set(2020, Calendar.JANUARY, 2);
        until = Calendar.getInstance();
        until.clear();
        until.set(2020, Calendar.JANUARY, 5);
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
     * Tests that getQuote correctly returns a valid List of StockQuotes in a date range.
     */
    @Test
    public void testGetQuotes(){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", from, until, IntervalEnum.DAILY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 4", 4, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(asLocalDateTime(from)) && !stockQuote.getDate().isAfter(asLocalDateTime(until));
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes for each hour within a date range.
     */
    @Test
    public void testGetQuotesHourly(){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", from, until, IntervalEnum.HOURLY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 73", 73, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(asLocalDateTime(from)) && !stockQuote.getDate().isAfter(asLocalDateTime(until));
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullCompany(){
        basicStockService.getQuote(null);
    }

    //Converts a Calendar to a LocalDateTime
    private LocalDateTime asLocalDateTime(Calendar calendar)
    {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
