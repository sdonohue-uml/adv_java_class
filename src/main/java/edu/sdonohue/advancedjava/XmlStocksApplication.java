package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.model.Quote;
import edu.sdonohue.advancedjava.model.QuotesDao;
import edu.sdonohue.advancedjava.xmltodb.InvalidXMLException;
import edu.sdonohue.advancedjava.xmltodb.Stocks;
import edu.sdonohue.advancedjava.xmltodb.XMLUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;

/**
 * Application that reads in a list of stock quotes from an XML file and stores them in a database.
 *
 * @author Sean Donohue
 */
public class XmlStocksApplication {

    /**
     * The main method for XmlStockApplication that reads stock quote data from the stock_info.xml file
     * and stores it in the database. No arguments.
     *
     * @param args none
     */
    public static void main(String[] args){
        try{
            File file = new File(XmlStocksApplication.class.getResource("/xml/stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/stock_info.xsd");
            for (Stocks.Quote xmlQuote : stocks.getQuotes()){
                Quote dbQuote = new Quote(xmlQuote);
                QuotesDao.updateOrInsertQuote(dbQuote);
            }
            printQuotes();
        } catch (IOException | InvalidXMLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //Output the list of Quotes from the database.
    private static void printQuotes(){
        StringBuilder header = new StringBuilder("StockQuotes from XML");
        System.out.println(header.toString());
        System.out.println("--------------------------------------------------------------------------------");
        for (Quote quote : QuotesDao.getQuoteList()){
            System.out.println(quote);
        }
    }

}
