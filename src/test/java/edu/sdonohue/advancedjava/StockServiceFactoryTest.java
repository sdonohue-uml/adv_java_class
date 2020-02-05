package edu.sdonohue.advancedjava;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for unit tests of the StockServiceFactory class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class StockServiceFactoryTest {

    /**
     * Test that getStockService correctly returns a StockService object.
     */
    @Test
    public void testGetStockService(){
        assertNotNull("StockService should not be null", StockServiceFactory.getStockService());
        assertTrue("Returned object should be an instance of StockService", StockServiceFactory.getStockService() instanceof StockService);
    }
}
