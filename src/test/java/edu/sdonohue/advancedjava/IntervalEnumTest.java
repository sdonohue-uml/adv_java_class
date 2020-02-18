package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.StockService.IntervalEnum;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the StockService.IntervalEnum internal class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class IntervalEnumTest {

    /**
     * Test that the fromHours() conversion method returns the correct interval for valid hour values, and
     * returns an empty Optional for invalid hour values.
     */
    @Test
    public void testFromHours(){
        assertEquals("Converting from 24 hours should return DAILY", Optional.of(IntervalEnum.DAILY), IntervalEnum.fromHours(24));
        assertEquals("Converting from 12 hours should return TWICE_DAILY", Optional.of(IntervalEnum.TWICE_DAILY), IntervalEnum.fromHours(12));
        assertEquals("Converting from 24 hours should return HOURLY", Optional.of(IntervalEnum.HOURLY), IntervalEnum.fromHours(1));
        assertEquals("Converting from 3 hours should return empty", Optional.empty(), IntervalEnum.fromHours(3));
    }
}
