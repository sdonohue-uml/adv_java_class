package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.StockService.IntervalEnum;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private Calendar testFrom;
    private Calendar testUntil;

    /**
     * Set up to initialized each test. Creates a BasicStockService.
     */
    @Before
    public void setup(){
        try {
            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse("01/02/2020");
            testFrom = Calendar.getInstance();
            testFrom.setTime(date1);
            Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse("01/05/2020");
            testUntil = Calendar.getInstance();
            testUntil.setTime(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
     * Tests that getQuote correctly returns a valid List of StockQuotes in a date range.
     */
    @Test
    public void testGetQuotes(){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", testFrom, testUntil, IntervalEnum.DAILY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 4", 4, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().before(testFrom.getTime()) && !stockQuote.getDate().after(testUntil.getTime());
            assertTrue("Date of StockQuote should be within the requested range", isBetween);
        }
    }

    /**
     * Tests that getQuote correctly returns a valid List of StockQuotes for each hour within a date range.
     */
    @Test
    public void testGetQuotesHourly(){
        List<StockQuote> stockQuotes = basicStockService.getQuote("TEST", testFrom, testUntil, IntervalEnum.HOURLY );
        assertNotNull("Get Quotes should not return null", stockQuotes);
        assertEquals("Number of StockQuotes returned should be 73", 73, stockQuotes.size());
        for (StockQuote stockQuote : stockQuotes){
            assertEquals("GetQuote should create a StockQuote for the requested company", "TEST", stockQuote.getCompanySymbol());
            boolean isBetween = !stockQuote.getDate().before(testFrom.getTime()) && !stockQuote.getDate().after(testUntil.getTime());
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
}
