package edu.sdonohue.advancedjava.userstocks;

import edu.sdonohue.advancedjava.service.userstocks.UserStockService;
import edu.sdonohue.advancedjava.service.userstocks.UserStockServiceFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for <CODE>activitiesService</CODE>
 */
public class UserStockServiceFactoryTest {

    @Test
    public void testFactory() {
        UserStockService instance = UserStockServiceFactory.getInstance();
        assertNotNull("Make sure factory works", instance);
    }
}
