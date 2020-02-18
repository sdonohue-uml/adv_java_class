package edu.sdonohue.advancedjava;

import org.jetbrains.annotations.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

/**
 * Defines the interface for services that will return StockQuotes
 *
 * @author Sean Donohue
 * @version 1.2
 */
public interface StockService {
    /**
     * Return the current price for a share of stock for the given symbol
     * @param symbol the stock symbol of the company you want a quote for.
     * e.g. APPL for APPLE
     * @return a <CODE>StockQuote </CODE> instance
     */
    @Nullable
    StockQuote getQuote(@NotNull String symbol);

    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param from the date of the first stock quote
     * @param until the date of the last stock quote
     * @param interval an IntervalEnum value for the time between each quote (e.g. StockService.IntervalEnum.DAILY)
     * @return a list of StockQuote instances. One for each day in the range specified.
     */
    @NotNull
    List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
                              @NotNull IntervalEnum interval);

    /**
     * Enumeration of the valid intervals between StockQuotes
     */
    enum IntervalEnum{
        HOURLY(1),
        TWICE_DAILY(12),
        DAILY(24);

        private final int hours;

        IntervalEnum(int hours){
            this.hours = hours;
        }

        public int getHours(){
            return hours;
        }

        @Override
        public String toString(){
            return "Every " + hours + " hours";
        }
    }
}

