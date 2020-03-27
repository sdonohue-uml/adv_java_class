package edu.sdonohue.advancedjava.service.stocks;


import edu.sdonohue.advancedjava.model.StockQuote;
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
public class BasicStockService extends AbstractStockService {

    /**
     * @inheritDoc
     */
    @Override
    @Nullable
    public StockQuote getQuote(@NotNull String symbol) {
        return generateQuote(symbol, LocalDateTime.now());
    }

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull LocalDateTime from, @NotNull LocalDateTime until,
                                     @NotNull IntervalEnum interval) {
        List<StockQuote> stocks = new LinkedList<>();
        for (LocalDateTime timeOfQuote = from.plusDays(0); !from.isAfter(until); timeOfQuote = interval.advance(timeOfQuote)){
            stocks.add(generateQuote(symbol, timeOfQuote));
        }
        return stocks;
    }

    //Utility method for generating StockQuotes for testing
    private StockQuote generateQuote(String symbol, LocalDateTime date){
        return new StockQuote(symbol,new BigDecimal("12.34"), date);
    }
}
