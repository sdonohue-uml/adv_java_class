package edu.sdonohue.advancedjava;


import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
                                     @NotNull IntervalEnum interval) {
        List<StockQuote> stocks = new LinkedList<>();
        for (Calendar timeOfQuote = (Calendar)from.clone(); !timeOfQuote.after(until); interval.advance(timeOfQuote)){
            stocks.add(generateQuote(symbol, timeOfQuote));
        }
        return stocks;
    }

    //Utility method for generating StockQuotes for testing
    private StockQuote generateQuote(String symbol, Calendar date){
        LocalDateTime dateAsLDT = LocalDateTime.now();
        if (date != null){
            dateAsLDT = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return new StockQuote(symbol,new BigDecimal("12.34"), dateAsLDT);
    }
}
