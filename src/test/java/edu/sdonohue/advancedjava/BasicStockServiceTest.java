package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.service.stocks.StockService.IntervalEnum;
import edu.sdonohue.advancedjava.service.stocks.BasicStockService;
import edu.sdonohue.advancedjava.model.StockQuote;
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
    private Calendar from; // 1/1/2019
    private Calendar until; // 12/31/2019

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        basicStockService = new BasicStockService();
        from = Calendar.getInstance();
        from.clear();
        from.set(2019, Calendar.JANUARY, 1);
        until = Calendar.getInstance();
        until.clear();
        until.set(2020, Calendar.JANUARY, 1);
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

    private void testGetQuotes(IntervalEnum interval, int expectedNumQuotes){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", from, until, interval);
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be " + expectedNumQuotes, expectedNumQuotes, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(asLocalDateTime(from)) && !stockQuote.getDate().isAfter(asLocalDateTime(until));
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of hourly StockQuotes in a date range.
     */
    @Test
    public void testGetQuotesHourly(){
        testGetQuotes(IntervalEnum.HOURLY, 8761);
    }

    /**
     * Tests that getQuote correctly returns a valid List of daily StockQuotes in a date range.
     */
    @Test
    public void testGetQuotesDaily(){
        testGetQuotes(IntervalEnum.DAILY, 366);
    }

    /**
     * Tests that getQuote correctly returns a valid List of monthly StockQuotes in a date range.
     */
    @Test
    public void testGetQuotesMonthly(){
        testGetQuotes(IntervalEnum.MONTHLY, 13);
    }

    /**
     * Tests that getQuote correctly returns a valid List of annual StockQuotes in a date range.
     */
    @Test
    public void testGetQuotesAnnually(){
        testGetQuotes(IntervalEnum.ANNUALLY, 2);
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
