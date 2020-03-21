package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.xml.InvalidXMLException;
import edu.sdonohue.advancedjava.xml.Stocks;
import edu.sdonohue.advancedjava.xml.XMLUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

public class XmlStocksApplication {

    public static void main(String[] args){
        try{
            File file = new File(XmlStocksApplication.class.getResource("/xml/stock_info.xml").toURI());
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class);
            printQuotes(stocks.getQuotes());
        } catch (IOException | InvalidXMLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //Output the list of StockQuotes.
    private static void printQuotes(@NotNull List<Stocks.Quote> quotes){
        StringBuilder header = new StringBuilder("StockQuotes from XML");
        System.out.println(header.toString());
        System.out.println("--------------------------------------------------------------------------------");
        for (Stocks.Quote quote : quotes){
            System.out.println(quote);
        }
    }

}
