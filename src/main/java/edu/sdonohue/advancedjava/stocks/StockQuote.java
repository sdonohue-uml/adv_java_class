package edu.sdonohue.advancedjava.stocks;



import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * Class for holding the price of a share of stock on a given date.
 *
 * @author Sean Donohue
 * @version 1.2
 */
@Immutable
public class StockQuote {
    //The price of the stock
    private final BigDecimal price;
    //The stock market symbol of the company
    private final String companySymbol;
    //The date the stock was that price
    private final LocalDateTime date;

    /**
     * Constructs a StockQuote with the given company stock market symbol,
     * the price in dollars and cents as a BigDecimal, and the date the price
     * quote is for.
     *  @param companySymbol The stock market symbol of the company
     * @param price The price of the stock in dollars
     * @param date The date the price quote is for
     */
    public StockQuote(@NotNull String companySymbol, @NotNull BigDecimal price, @NotNull LocalDateTime date){
        if (companySymbol == null){
            throw new NullPointerException("Company Symbol may not be null");
        }
        if (price == null){
            throw new NullPointerException("Price may not be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price may not be negative");
        }
        if (date == null){
            throw new NullPointerException("Date may not be null");
        }
        this.companySymbol = companySymbol;
        this.price = price;
        this.date = LocalDateTime.from(date);
    }

    /**
     * Get the price of the stock in dollars.
     *
     * @return The price of the stock
     */
    public BigDecimal getPrice(){
        return price;
    }

    /**
     * Get the stock market symbol of the company.
     *
     * @return The company stock market symbol
     */
    @NotNull
    public String getCompanySymbol(){
        return companySymbol;
    }

    /**
     * Get the date that the price quote was for.
     *
     * @return The date of the quote
     */
    @NotNull
    public LocalDateTime getDate(){
        return LocalDateTime.from(date);
    }

    /**
     * Get the date that the price quote was for as a Calendar instance.
     *
     * @return The date of the quote
     */
    @NotNull
    public Calendar getDateAsCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfYear(),
                date.getHour(), date.getMinute(), date.getSecond());
        return calendar;
    }

    /**
     * Get a string representation of the StockQuote for output
     * or troubleshooting. Contains the company symbol, the date,
     * and the price.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("StockQuote for ");
        stringBuilder.append(getCompanySymbol());
        stringBuilder.append(" on ");
        stringBuilder.append(getDate().format(DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm:ss")));
        stringBuilder.append(" = $");
        stringBuilder.append(getPrice());
        return stringBuilder.toString();
    }

}
