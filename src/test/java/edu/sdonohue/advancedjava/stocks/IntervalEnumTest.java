package edu.sdonohue.advancedjava.stocks;

import edu.sdonohue.advancedjava.service.stocks.StockService.IntervalEnum;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the StockService.IntervalEnum internal class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class IntervalEnumTest {
    private LocalDateTime startDate; //12/31/2019 12:00am

    @Before
    public void setup(){
        startDate = LocalDateTime.of(2019, Month.DECEMBER, 31, 0, 0, 0);
    }

    private void advanceTest(IntervalEnum interval, int year, int month, int day, int hour){
        LocalDateTime actual = interval.advance(startDate);
        LocalDateTime expected = LocalDateTime.of(year, month, day, hour, 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
        assertEquals(interval.toString() + " should advance date to " + expected.format(formatter) +
                        " but is " + actual.format(formatter), 0, actual.compareTo(expected));
    }

    /**
     * Test that IntervalEnum.HOURLY advances the date and time correctly
     */
    @Test
    public void testAdvanceHourly(){
        advanceTest(IntervalEnum.HOURLY, 2019, 11, 31, 1);
    }

    /**
     * Test that IntervalEnum.DAILY advances the date and time correctly
     */
    @Test
    public void testAdvanceDaily(){
        advanceTest(IntervalEnum.DAILY, 2020, 0, 1, 0);
    }

    /**
     * Test that IntervalEnum.WEEKLY advances the date and time correctly
     */
    @Test
    public void testAdvanceWeekly(){
        advanceTest(IntervalEnum.WEEKLY, 2020, 0, 7, 0);
    }

    /**
     * Test that IntervalEnum.BI_WEEKLY advances the date and time correctly
     */
    @Test
    public void testAdvanceBiWeekly(){
        advanceTest(IntervalEnum.BI_WEEKLY, 2020, 0, 14, 0);
    }

    /**
     * Test that IntervalEnum.MONTHLY advances the date and time correctly
     */
    @Test
    public void testAdvanceMonthly(){
        advanceTest(IntervalEnum.MONTHLY, 2020, 0, 31, 0);
    }

    /**
     * Test that IntervalEnum.BI_MONTHLY advances the date and time correctly
     */
    @Test
    public void testAdvanceBiMonthly(){
        advanceTest(IntervalEnum.BI_MONTHLY, 2020, 1, 29, 0);
    }

    /**
     * Test that IntervalEnum.SEMI_ANNUALLY advances the date and time correctly
     */
    @Test
    public void testAdvanceSemiAnnually(){
        advanceTest(IntervalEnum.SEMI_ANNUALLY, 2020, 5, 30, 0);
    }

    /**
     * Test that IntervalEnum.ANNUALLY advances the date and time correctly
     */
    @Test
    public void testAdvanceAnnually(){
        advanceTest(IntervalEnum.ANNUALLY, 2020, 11, 31, 0);
    }

    /**
     * Test that IntervalEnum.BI_ANNUALLY advances the date and time correctly
     */
    @Test
    public void testAdvanceBiAnnually(){
        advanceTest(IntervalEnum.BI_ANNUALLY, 2021, 11, 31, 0);
    }
}
