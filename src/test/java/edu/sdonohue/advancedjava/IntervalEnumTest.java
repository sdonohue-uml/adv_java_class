package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.StockService.IntervalEnum;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the StockService.IntervalEnum internal class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class IntervalEnumTest {
    private Calendar startDate; //12/31/2019 12:00am

    @Before
    public void setup(){
        startDate = Calendar.getInstance();
        startDate.clear();
        startDate.set(2019, Calendar.DECEMBER, 31);
    }

    private void advanceTest(IntervalEnum interval, int year, int month, int day, int hour){
        interval.advance(startDate);
        Calendar expected = Calendar.getInstance();
        expected.clear();
        expected.set(year, month, day, hour,0, 0);
        Date expectedAsDate = expected.getTime();
        Date actualAsDate = startDate.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        assertEquals(interval.toString() + " should advance date to " + dateFormat.format(expectedAsDate) +
                        " but is " + dateFormat.format(actualAsDate), 0, startDate.compareTo(expected));
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
