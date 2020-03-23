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
 * Unit tests for the XmlUtils class.
 *
 * @author Sean Donohue
 */
public class XmlUtilsTest {

    /**
     * Verify that unmarshalling from XML without validation works correctly.
     */
    @Test
    public void testUnmarshallNoValidation(){
        try {
            File file = new File(XmlStocksApplication.class.getResource("/xml/test_stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class);
            assertTrue("List of Quotes should not be null", stocks.getQuotes() != null);
            assertEquals("Number of Quotes should be 49", 49, stocks.getQuotes().size());
        } catch (InvalidXMLException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify that unmarshalling from XML with validation works correctly.
     */
    @Test
    public void testUnmarshallWithValidation(){
        try {
            File file = new File(XmlStocksApplication.class.getResource("/xml/test_stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/test_stock_info.xsd");
            assertTrue("List of Quotes should not be null", stocks.getQuotes() != null);
            assertEquals("Number of Quotes should be 49", 49, stocks.getQuotes().size());
        } catch (InvalidXMLException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify that unmarshalling XML with missing schema throws an exception.
     */
    @Test (expected = IllegalStateException.class)
    public void testUnmarshallMissingSchema(){
        try {
            File file = new File(XmlStocksApplication.class.getResource("/xml/test_stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/not_here.xsd");
        } catch (InvalidXMLException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verify that unmarshalling invalid XML throws an exception.
     */
    @Test (expected = InvalidXMLException.class)
    public void testUnmarshallBadXml() throws IOException, InvalidXMLException, URISyntaxException {
        File file = new File(XmlStocksApplication.class.getResource("/xml/test_bad_stock_info.xml").toURI());
        String xml = new String(Files.readAllBytes(file.toPath()));
        Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/test_stock_info.xsd");
    }

    /**
     * Verify that unmarshalling invalid XML schema throws an exception.
     */
    @Test (expected = InvalidXMLException.class)
    public void testUnmarshallBadSchema() throws IOException, InvalidXMLException, URISyntaxException {
        File file = new File(XmlStocksApplication.class.getResource("/xml/test_stock_info.xml").toURI());
        String xml = new String(Files.readAllBytes(file.toPath()));
        Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/test_bad_stock_info.xsd");
    }
}
