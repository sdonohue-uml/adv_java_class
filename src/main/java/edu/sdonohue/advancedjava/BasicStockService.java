package edu.sdonohue.advancedjava;


import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Service that returns the current price of a given stock.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class BasicStockService implements StockService {

    /**
     * @inheritDoc
     */
    @Override
    @Nullable
    public StockQuote getQuote(@NotNull String symbol) {
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
    @NotNull
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
                                     @NotNull IntervalEnum interval) {
        List<StockQuote> stocks = new LinkedList<>();
        for (Calendar timeOfQuote = (Calendar)from.clone(); !timeOfQuote.after(until);
             timeOfQuote.add(Calendar.HOUR_OF_DAY, interval.getHours())){
            stocks.add(generateQuote(symbol, timeOfQuote));
        }
        return stocks;
    }
}
