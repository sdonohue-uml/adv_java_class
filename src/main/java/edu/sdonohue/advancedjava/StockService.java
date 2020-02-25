package edu.sdonohue.advancedjava;

import org.jetbrains.annotations.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
                              @NotNull IntervalEnum interval) throws StockServiceException;

    /**
     * Enumeration of the valid intervals between StockQuotes
     */
    enum IntervalEnum{
        HOURLY(1),
        TWICE_DAILY(12),
        DAILY(24);

        //The length of the interval in hours
        private final int hours;
        //Lookup for finding the interval that matches a number of hours
        private static final Map<Integer, IntervalEnum> hoursToInterval = Stream.of(values()).collect(
                Collectors.toMap(IntervalEnum::getHours, e -> e));

        IntervalEnum(int hours){
            this.hours = hours;
        }

        /**
         * Get the number of hours in the interval.
         *
         * @return The length of the interval in hours
         */
        public int getHours(){
            return hours;
        }

        /**
         * Convert a number of hours into an Optional that contains the matching IntervalEnum
         * if it exists, or an empty Optional otherwise.
         * @param hours The length of the interval in hours
         * @return An Optional of IntervalEnum
         */
        @NotNull
        public static Optional<IntervalEnum> fromHours(int hours){
            return Optional.ofNullable(hoursToInterval.get(hours));
        }

        /**
         * @inheritDoc
         *
         */
        @Override
        public String toString(){
            return "Every " + hours + " hours";
        }
    }
}

