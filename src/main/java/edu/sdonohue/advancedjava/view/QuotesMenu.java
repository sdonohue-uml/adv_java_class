package edu.sdonohue.advancedjava.view;


import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.service.stocks.StockServiceFactory;

import java.util.Scanner;

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
        System.out.println("Enter the Stock Symbol of the Company");
        try (Scanner scanner = new Scanner(System.in)) {
            String symbol = scanner.next();
            if (symbol != null && symbol.length() > 0) {
                try {
                    StockQuote quote = StockServiceFactory.getStockService().getQuote(symbol);
                    if (quote != null) {
                        System.out.println(quote);
                    } else {
                        System.out.println("No Stock Prices found for " + symbol);
                    }
                } catch (StockServiceException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No Stock Symbol entered");
            }
        }
        display();
    }

    public void getQuotes(){
        System.out.println("Get Quotes Selected");
        display();
    }
}
