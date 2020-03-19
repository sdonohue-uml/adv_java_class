package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.service.userstocks.UserStockService;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for UserStockServiceFactory class.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class UserStockServiceFactoryTest {

    /**
     * Verify that factory returns an instance of UserStockService.
     */
    @Test
    public void testFactory() {
        UserStockService instance = UserStockServiceFactory.getInstance();
        assertNotNull("UserStockService should not be null", instance);
    }
}
