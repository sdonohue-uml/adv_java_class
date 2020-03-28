package edu.sdonohue.advancedjava.service.stocks;


import javax.validation.constraints.NotNull;

/**
 * Factory class for creating instances of StockService
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class StockServiceFactory {

    /**
     * Returns a StockService that can be used to retrieve StockQuotes.
     *
     * @return A instance of StockService
     */
    @NotNull
    public static StockService getStockService(){
        return new RestStockService();
    }
}
