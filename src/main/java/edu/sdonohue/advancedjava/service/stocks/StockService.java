package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import org.jetbrains.annotations.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    StockQuote getQuote(@NotNull String symbol) throws StockServiceException;

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
    List<StockQuote> getQuote(@NotNull String symbol, @NotNull LocalDateTime from, @NotNull LocalDateTime until,
                              @NotNull IntervalEnum interval) throws StockServiceException;


    /**
     * Enumeration of the valid intervals between StockQuotes
     */
    enum IntervalEnum{
        HOURLY(1, ChronoUnit.HOURS),
        DAILY(1, ChronoUnit.DAYS),
        WEEKLY(1, ChronoUnit.WEEKS),
        BI_WEEKLY(2, ChronoUnit.WEEKS),
        MONTHLY(1, ChronoUnit.MONTHS),
        BI_MONTHLY(2, ChronoUnit.MONTHS),
        SEMI_ANNUALLY(6, ChronoUnit.MONTHS),
        ANNUALLY(1, ChronoUnit.YEARS),
        BI_ANNUALLY(2, ChronoUnit.YEARS);

        private final int quantity;
        private final ChronoUnit chronoUnit;

        IntervalEnum(int quantity, ChronoUnit chronoUnit){
            this.quantity = quantity;
            this.chronoUnit = chronoUnit;
        }

        /**
         * Advances the provided LocalDateTime forward by the interval.
         *
         * @param startDate The date to be advanced from
         * @return A new LocalDateTime object with the advanced date and time
         */
        @NotNull
        public LocalDateTime advance(@NotNull LocalDateTime startDate) {
            return startDate.plus(quantity, chronoUnit);
        }

        /**
         * Returns a print friendly name by returning toString() in proper case.
         *
         * @return The name of the interval in proper case
         */
        @Override
        public String toString(){
            String name = name();
            return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }
}

