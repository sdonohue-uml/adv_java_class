package edu.sdonohue.advancedjava.view;


import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.service.stocks.StockServiceFactory;

import java.time.LocalDateTime;
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
        commands.put(3, new MenuCommand("Return to Main Menu", new Runnable() {
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

        LocalDateTime startDate = promptForDate("Enter the Start Date (e.g. 1/15/2020)");
        if (startDate == null){

        }

    }
}
