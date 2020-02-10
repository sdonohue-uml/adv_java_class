package edu.sdonohue.advancedjava;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Service that returns the current price of a given stock.
 *
 * @author Sean Donohue
 * @version 1.1
 */
public class BasicStockService implements StockService {

    /**
     * @inheritDoc
     */
    @Override
    public StockQuote getQuote(String symbol) {
        return generateQuote(symbol, Calendar.getInstance());
    }

    //Utility method for generating StockQuotes for testing
    private StockQuote generateQuote(String symbol, Calendar date){
        return new StockQuote(symbol, 12.34f, date.getTime());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until) {
        List<StockQuote> stocks = new LinkedList<>();
        for (Calendar day = (Calendar)from.clone(); !day.after(until); day.add(Calendar.DATE, 1)){
            stocks.add(generateQuote(symbol, day));
        }
        return stocks;
    }
}
