package edu.sdonohue.advancedjava.stocks;

import edu.sdonohue.advancedjava.service.stocks.StockService.IntervalEnum;
import edu.sdonohue.advancedjava.service.stocks.BasicStockService;
import edu.sdonohue.advancedjava.model.StockQuote;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for unit tests of the BasicStockService class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class BasicStockServiceTest {
    private final BasicStockService basicStockService = new BasicStockService();
    private final LocalDateTime from = LocalDateTime.of(
            2019, Month.JANUARY, 1, 0, 0, 0); // 1/1/2019
    private final LocalDateTime until = LocalDateTime.of(
            2020, Month.JANUARY, 1, 0, 0, 0); // 12/31/2019

    /**
     * Tests that getQuote correctly returns a valid StockQuote.
     */
    @Test
    public void testGetQuote(){
        StockQuote stockQuote = basicStockService.getQuote("TEST");
        assertNotNull("Get Quote should not return null", stockQuote);
        assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
    }

    //Utility method that gets Quotes for a given interval and tests the result
    private void testGetQuotes(IntervalEnum interval, int expectedNumQuotes){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", from, until, interval);
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be " + expectedNumQuotes, expectedNumQuotes, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(from) && !stockQuote.getDate().isAfter(until);
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
}
