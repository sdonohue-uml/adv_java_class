package edu.sdonohue.advancedjava.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.RestStockService;
import edu.sdonohue.advancedjava.service.stocks.StockService;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the RestStockService class.
 *
 * @author Sean Donohue
 */
public class RestStockServiceTest {
    private final RestStockService restStockService = new RestStockService();
    private final LocalDateTime from = LocalDateTime.of(
            2020, Month.JANUARY, 2, 0, 0, 0); // 1/2/2020
    private final LocalDateTime until = LocalDateTime.of(
            2020, Month.JANUARY, 5, 0, 0, 0); // 1/5/2020


    /**
     * Tests that getQuote correctly returns the right StockQuote.
     */
    @Test
    public void testGetQuote() throws StockServiceException {
        StockQuote stockQuote = restStockService.getQuote("AMZN");
        assertNotNull("Get Quote should not return null", stockQuote);
        assertEquals("GetQuote should return a StockQuote for the requested company",
                "AMZN", stockQuote.getCompanySymbol());
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes in a date range.
     */
    @Test
    public void testGetQuotes() throws StockServiceException {
        List<StockQuote> stockQuotes = restStockService.getQuote("AMZN", from, until, StockService.IntervalEnum.DAILY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 4", 4, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should return a StockQuote for the requested company", "AMZN", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(from) && !stockQuote.getDate().isAfter(until);
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes for each hour within a date range.
     */
    @Test
    public void testGetQuotesHourly() throws StockServiceException {
        List<StockQuote> stockQuotes = restStockService.getQuote("AMZN", from, until, StockService.IntervalEnum.HOURLY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 73", 73, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should return a StockQuote for the requested company", "AMZN", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().isBefore(from) && !stockQuote.getDate().isAfter(until);
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Test that an Exception is thrown if a null company is passed.
     */
    @Test (expected = NullPointerException.class)
    public void testNullCompany() throws StockServiceException {
        restStockService.getQuote(null);
    }

    /**
     * Test that an Exception is thrown if a non-existent company is passed.
     */
    @Test (expected = StockServiceException.class)
    public void testBadCompany() throws StockServiceException {
        restStockService.getQuote("NOTTHERE");
    }
}
