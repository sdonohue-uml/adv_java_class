package edu.sdonohue.advancedjava.view;


import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockService;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.service.stocks.StockServiceFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static edu.sdonohue.advancedjava.view.CliUtils.*;

public class QuotesMenu extends AbstractMenu {

    public QuotesMenu(Menu parent){
        this.parentMenu = parent;
        initCommands();
        initHeader();
    }

    @Override
    public void initHeader() {
        header = "Stock Quotes Menu";
    }

    @Override
    public void initCommands() {
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

    public void getQuote(){
        String symbol = promptForText("Enter the Stock Symbol of the Company");

        if (symbol == null || symbol.length() == 0) {
            error("No Stock Symbol entered");
            return;
        }

        try {
            StockQuote quote = StockServiceFactory.getStockService().getQuote(symbol);
            if (quote != null) {
                result("(Datasource: " + StockServiceFactory.getDataSource() + ")");
                result(quote.toString());
            } else {
                result("No Stock Prices found for " + symbol);
            }
        } catch (StockServiceException e) {
            // todo: output errors and recover
            e.printStackTrace();
        }

    }

    public void getQuotes(){
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
            List<StockQuote> quotes = StockServiceFactory.getStockService().getQuote(symbol, startDate, endDate, interval);
            if (quotes == null || quotes.size() == 0){
                result("No Stock Prices found for " + symbol + " within that date range");
            } else {
                result("(Datasource: " + StockServiceFactory.getDataSource() + ")");
                for (StockQuote quote : quotes){
                    result(quote.toString());
                }
            }
        } catch (StockServiceException e) {
            // todo: output errors and recover
            e.printStackTrace();
        }

    }

    protected void setDataSource(){
        result("Current Datasource: " + StockServiceFactory.getDataSource());
        StockServiceFactory.DataSource dataSource = (StockServiceFactory.DataSource) promptForSelection(
                "Which Datasource would you like to use?",
                Arrays.asList(StockServiceFactory.DataSource.values()));
        if (dataSource == null){
            error("Error reading Datasource selection");
        } else {
            StockServiceFactory.setDataSource(dataSource);
        }
    }

    protected void uploadXml(){
        System.out.println("Upload XML Selected");
    }
}
