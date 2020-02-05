package edu.sdonohue.advancedjava;

/**
 * Service that returns the current price of a given stock.
 *
 * @author Sean Donohue
 * @version 1.0
 */
public class BasicStockService implements StockService {

    /**
     * @inheritDoc
     */
    @Override
    public StockQuote getQuote(String symbol) {
        return new StockQuote(symbol, 12.34f);
    }
}
