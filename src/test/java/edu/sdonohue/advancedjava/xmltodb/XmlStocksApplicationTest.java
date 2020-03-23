package edu.sdonohue.advancedjava.xmltodb;

import edu.sdonohue.advancedjava.XmlStocksApplication;
import org.junit.Test;

/**
 * Unit tests for the XmlStocksApplication class.
 *
 * @author Sean Donohue
 */
public class XmlStocksApplicationTest {

    /**
     * Test that the main method with no arguments runs with no exceptions.
     */
    @Test
    public void testMainMethodNoArgs(){
        XmlStocksApplication.main(new String[]{});
    }

    /**
     * Test that the main method with arbitrary arguments runs with no exceptions
     */
    @Test
    public void testMainMethodArgs(){
        XmlStocksApplication.main(new String[]{"doesNothing"});
    }
}
