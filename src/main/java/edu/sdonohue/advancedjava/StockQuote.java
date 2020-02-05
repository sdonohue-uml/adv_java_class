package edu.sdonohue.advancedjava;

/**
 * Class for holding the current price of a share of stock
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class StockQuote {
    //Store the price as an integer for more precise calculations
    private final int priceInCents;
    //The stock market symbol of the company
    private final String companySymbol;

    /**
     * Constructs a StockQuote with the given company stock market symbol
     * and the price in dollars and cents as a float. Any extra digits beyond
     * 2 decimal places in the price will be ignored.
     *
     * @param companySymbol The stock market symbol of the company
     * @param priceInDollars The price of the stock to 2 decimal places
     */
    public StockQuote(String companySymbol, float priceInDollars){
        this.companySymbol = companySymbol;
        this.priceInCents = (int)(priceInDollars * 100);
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
    public String getCompanySymbol(){
        return companySymbol;
    }

}
