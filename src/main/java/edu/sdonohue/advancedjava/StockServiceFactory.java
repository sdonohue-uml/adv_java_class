package edu.sdonohue.advancedjava;

/**
 * Factory class for creating instances of StockService
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class StockServiceFactory {

    /**
     * Returns a StockService that can be used to retrieve StockQuotes.
     *
     * @return A instance of StockService
     */
    public static StockService getStockService(){
        return new BasicStockService();
    }
}
