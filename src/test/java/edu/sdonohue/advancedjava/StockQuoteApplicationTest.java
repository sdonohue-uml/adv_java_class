package edu.sdonohue.advancedjava;

import org.junit.Test;

/**
 * Test class for unit tests of the StockQuoteApplication class.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class StockQuoteApplicationTest {

    /**
     * Test that no exceptions are thrown when called with correct arguments.
     */
    @Test
    public void testMain(){
        StockQuoteApplication.main(new String[]{"TEST", "01/02/2020", "01/05/2020"});
    }

    /**
     * Test that an Exception is thrown if no arguments are passed.
     */
    @Test(expected = NullPointerException.class)
    public void testMainNullArguments() {
        StockQuoteApplication.main(null);
    }

    /**
     * Test that an Exception is thrown if 2 arguments are passed.
     */
    @Test(expected = NullPointerException.class)
    public void testMainTwoArguments(){
        StockQuoteApplication.main(new String[]{"TEST", "01/02/2020"});
    }

    /**
     * Test that an Exception is thrown if too many arguments are passed.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMainTooManyArguments(){
        StockQuoteApplication.main(new String[]{"TEST", "01/02/2020", "01/05/2020", "TRUE"});
    }

    /**
     * Test that an Exception is thrown if too many arguments are passed.
     */
    @Test(expected = NullPointerException.class)
    public void testMainBlankCompany(){
        StockQuoteApplication.main(new String[]{"", "01/02/2020", "01/05/2020"});
    }

    /**
     * Test that an Exception is thrown if bad dates are passed.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMainBadDates(){
        StockQuoteApplication.main(new String[]{"TEST", "31/01/2020", "01/05/2020"});
    }

    /**
     * Test that an Exception is thrown if dates are passed in the wrong order.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMainBadDateOrder(){
        StockQuoteApplication.main(new String[]{"TEST", "01/05/2020", "01/02/2020"});
    }

    /**
     * Test that an Exception is thrown if end date is in the future.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMainFutureDate(){
        StockQuoteApplication.main(new String[]{"TEST", "01/02/2020", "01/02/2021"});
    }
}
