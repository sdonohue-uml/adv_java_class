package edu.sdonohue.advancedjava.xmltodb;

import edu.sdonohue.advancedjava.XmlStocksApplication;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Stocks class.
 *
 * @author Sean Donohue
 */
public class StocksTest {

    /**
     * Verify that getQuotes returns a valid list of Stocks.Quote when unmarshalled from XML.
     */
    @Test
    public void testGetQuotes(){
        try {
            File file = new File(XmlStocksApplication.class.getResource("/xml/stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class);
            assertTrue("List of Quotes should not be null", stocks.getQuotes() != null);
            assertEquals("Number of Quotes should be 49", 49, stocks.getQuotes().size());
        } catch (InvalidXMLException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
