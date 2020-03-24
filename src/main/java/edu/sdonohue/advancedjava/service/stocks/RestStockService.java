package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of StockService that retrieves stock data from a RESTful service.
 *
 * @author Sean Donohue
 */
public class RestStockService extends AbstractStockService {


    @Override
    public @Nullable StockQuote getQuote(@NotNull String symbol) throws StockServiceException {
        // Build the query string (very simple example!)
        String queryString = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY";
        queryString += "&symbol=" + symbol;
        queryString += "&apikey=CBMF2NOVDMTHR1I1";

        // Set up the connection to the online service
        try {
            java.net.URL connection_url = new java.net.URL(queryString);
            InputStream stream = connection_url.openStream();

            // Stream the results
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            // Store the results in a string to return (default results are json)
            String resultsString = "";
            String line = reader.readLine();
            while (line != null) {
                resultsString += line;
                line = reader.readLine();
            }
            System.out.println(resultsString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, IntervalEnum interval) throws StockServiceException {
        // Build the query string (very simple example!)
        String queryString = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY";
        queryString += "&symbol=" + symbol;
        queryString += "&apikey=Demo";

        // Set up the connection to the online service
        try {
            java.net.URL connection_url = new java.net.URL(queryString);
            InputStream stream = connection_url.openStream();

            // Stream the results
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            // Store the results in a string to return (default results are json)
            String resultsString = "";
            String line = reader.readLine();
            while (line != null) {
                resultsString += line;
                line = reader.readLine();
            }
            System.out.println(resultsString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
