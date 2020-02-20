package edu.sdonohue.advancedjava;



import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Class for holding the price of a share of stock on a given date.
 *
 * @author Sean Donohue
 * @version 1.2
 */
@Immutable
public class StockQuote {
    //Store the price as an integer for more precise calculations
    private final int priceInCents;
    //The stock market symbol of the company
    private final String companySymbol;
    //The date the stock was that price;
    private final LocalDateTime date;

    /**
     * Constructs a StockQuote with the given company stock market symbol,
     * the price in dollars and cents as a float, and the date the price
     * quote is for. Any extra digits beyond 2 decimal places in the price
     * will be ignored.
     *  @param companySymbol The stock market symbol of the company
     * @param priceInDollars The price of the stock to 2 decimal places
     * @param date The date the price quote is for.
     */
    public StockQuote(@NotNull String companySymbol, float priceInDollars, @NotNull LocalDateTime date){
        if (companySymbol == null){
            throw new NullPointerException("Company Symbol may not be null");
        }
        if (priceInDollars < 0 || priceInDollars*100 > Integer.MAX_VALUE){
            throw new IllegalArgumentException("Invalid price");
        }
        if (date == null){
            throw new NullPointerException("Date may not be null");
        }
        this.companySymbol = companySymbol;
        this.priceInCents = (int)(priceInDollars * 100);
        this.date = LocalDateTime.from(date);
    }

    /**
     * Get the price of the stock in dollars.
     *
     * @return The price of the stock
     */
    public float getPriceInDollars(){
        return ((float)priceInCents)/100;
    }

    /**
     * Ge the price of the stock in cents.
     *
     * @return The price of the stock
     */
    public int getPriceInCents(){
        return priceInCents;
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
        stringBuilder.append(getPriceInDollars());
        return stringBuilder.toString();
    }

}
