package edu.sdonohue.advancedjava.view;


import edu.sdonohue.advancedjava.model.Quote;
import edu.sdonohue.advancedjava.model.QuotesDao;
import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockService;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.service.stocks.StockServiceFactory;
import edu.sdonohue.advancedjava.util.DatabaseConnectionException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import edu.sdonohue.advancedjava.xmltodb.InvalidXMLException;
import edu.sdonohue.advancedjava.xmltodb.Stocks;
import edu.sdonohue.advancedjava.xmltodb.XMLUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static edu.sdonohue.advancedjava.service.stocks.StockServiceFactory.*;
import static edu.sdonohue.advancedjava.view.CliUtils.*;

/**
 * A menu that displays options related to getting StockQuotes.
 *
 * @author Sean Donohue
 */
public class QuotesMenu extends AbstractMenu {

    /**
     * Constructor that creates a QuotesMenu with a parent menu. The
     * parent menu is displayed when this menu is exited.
     *
     * @param parent The parent menu
     */
    public QuotesMenu(Menu parent){
        super();
        this.parentMenu = parent;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initHeader() {
        header = "Stock Quotes Menu";
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void initCommands() {
        commands.put(1, new MenuCommand("Get a current Quote", new Runnable() {
            @Override
            public void run() {
                QuotesMenu.this.getQuote();
            }
        }));
        commands.put(2, new MenuCommand("Get Historical Quotes", new Runnable() {
            @Override
            public void run() {
                QuotesMenu.this.getQuotes();
            }
        }));
        commands.put(3, new MenuCommand("Change Data Source", new Runnable() {
            @Override
            public void run() {
                QuotesMenu.this.setDataSource();
            }
        }));
        commands.put(4, new MenuCommand("Upload XML Data", new Runnable() {
            @Override
            public void run() {
                QuotesMenu.this.uploadXml();
            }
        }));
        commands.put(5, new MenuCommand("Return to Main Menu", new Runnable() {
            @Override
            public void run() {
                QuotesMenu.this.returnToParent();
            }
        }));
    }

    // Menu command to get a current StockQuote for a company.
    private void getQuote(){
        String symbol = promptForText("Enter the Stock Symbol of the Company");

        if (symbol == null || symbol.length() == 0) {
            error("No Stock Symbol entered");
            return;
        }

        try {
            StockQuote quote = getStockService().getQuote(symbol);
            if (quote != null) {
                result("(Datasource: " + getDataSource() + ")");
                result(quote.toString());
                if (getDataSource().equals(DataSource.UNIBIT) && promptToSave()){
                    saveQuote(quote);
                }
            } else {
                result("No Stock Prices found for " + symbol);
            }
        } catch (StockServiceException e) {
            if (e.getCause() != null){
                result(e.getCause().getMessage());
            }
            result("No Stock Prices found for " + symbol);
        }

    }

    // Menu command to get historical StockQuotes for a company at a given interval across a date range.
    private void getQuotes(){
        String symbol = promptForText("Enter the Stock Symbol of the Company");
        if (symbol == null || symbol.length() == 0) {
            error("No Stock Symbol entered");
            return;
        }

        LocalDateTime startDate = promptForDate("Enter the Start Date (e.g. 01/15/2020)");
        if (startDate == null){
            error("Error recognizing Start Date");
            return;
        }

        LocalDateTime endDate = promptForDate("Enter the End Date (e.g. 01/30/2020)");
        if (endDate == null){
            error("Error recognizing End Date");
            return;
        }

        if (startDate.isAfter(endDate)){
            error("The Start Date must be before the End Date");
            return;
        }
        if (endDate.isAfter(LocalDateTime.now())){
            error("The End Date must not be after today");
            return;
        }

        StockService.IntervalEnum interval = (StockService.IntervalEnum) promptForSelection(
                "Enter the number of the interval you wish to use",
                Arrays.asList(StockService.IntervalEnum.values()));
        if (interval == null){
            error("Interval Selection is invalid");
            return;
        }

        try {
            List<StockQuote> quotes = getStockService().getQuote(symbol, startDate, endDate, interval);
            if (quotes == null || quotes.size() == 0){
                result("No Stock Prices found for " + symbol + " within that date range");
            } else {
                result("(Datasource: " + getDataSource() + ")");
                for (StockQuote quote : quotes){
                    result(quote.toString());
                }
                if (getDataSource().equals(DataSource.UNIBIT) && promptToSave()){
                    saveQuotes(quotes);
                }
            }
        } catch (StockServiceException e) {
            if (e.getCause() != null){
                result(e.getCause().getMessage());
            }
            result("No Stock Prices found for " + symbol);
        }

    }

    // Prompt the user to see if they want to save online stock results to the database
    private boolean promptToSave(){
        boolean save = false;
        String response = promptForText("Do you want to save this data to the database? (Y/N)");
        if (response != null && response.toLowerCase().startsWith("y")){
            save = true;
        }
        return save;
    }


    // Save a list of StockQuotes to the database
    private void saveQuotes(List<StockQuote> quotes){
        if (quotes != null){
            for (StockQuote quote : quotes){
                saveQuote(quote);
            }
        }
    }

    // Save a StockQuote to the database
    private void saveQuote(StockQuote quote){
        Quote dbQuote = new Quote(quote);
        boolean saved = QuotesDao.updateOrInsertQuote(dbQuote);
        if (saved){
            result(dbQuote + " saved to database");
        }
    }

    // Menu command to change the datasource that StockQuote are retrieved from.
    private void setDataSource(){
        result("Current Datasource: " + getDataSource());
        DataSource dataSource = (DataSource) promptForSelection(
                "Which Datasource would you like to use?",
                Arrays.asList(DataSource.values()));
        if (dataSource == null){
            error("Error reading Datasource selection");
        } else {
            StockServiceFactory.setDataSource(dataSource);
        }
    }

    // Menu command to upload an XML file of StockQuotes to the database.
    private void uploadXml(){
        String fileName = promptForText(
                "Enter the path and filename of the XML file you want to import (e.g. C:\\test\\stock_data.xml)");
        if (fileName == null || fileName.length() == 0){
            error("Error reading filename");
            return;
        }

        try{
            File file = new File(fileName);
            String xml = new String(Files.readAllBytes(file.toPath()));
            Stocks stocks = XMLUtils.unmarshall(xml, Stocks.class, "/xml/stock_info.xsd");
            for (Stocks.Quote xmlQuote : stocks.getQuotes()){
                Quote dbQuote = new Quote(xmlQuote);
                QuotesDao.updateOrInsertQuote(dbQuote);
            }
            result("Added " + stocks.getQuotes().size() + " quotes to the database");
        } catch (IOException e) {
            error("XML File not found");
        } catch (InvalidXMLException e) {
            error("XML File format is incorrect");
        }
    }
}
