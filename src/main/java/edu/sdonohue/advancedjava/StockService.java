package edu.sdonohue.advancedjava;

/**
 * Defines the interface for services that will return StockQuotes
 *
 * @author Sean Donohue
 * @version 1.0
 */
public interface StockService {
    /**
     * Return the current price for a share of stock for the given symbol
     * @param symbol the stock symbol of the company you want a quote for.
     * e.g. APPL for APPLE
     * @return a <CODE>StockQuote </CODE> instance
     */
    StockQuote getQuote(String symbol);
}
