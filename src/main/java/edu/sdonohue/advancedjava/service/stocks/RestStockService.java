package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import javax.validation.constraints.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * An implementation of StockService that retrieves stock data from a REST service.
 *
 * @author Sean Donohue
 */
public class RestStockService extends AbstractStockService {

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public StockQuote getQuote(@NotNull String symbol) throws StockServiceException {
        if (symbol == null){
            throw new NullPointerException("Compnay symbol must not be null");
        }

        String queryString = "https://api.unibit.ai/v2/stock/historical";
        queryString += "?tickers=" + symbol;
        queryString += "&dataType=json";
        queryString += "&accessKey=Lm0BuvDvURYy_xjVVbz3qRSRGZrQ_Cfg";

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
            List<StockQuote> list = parseJson(resultsString, symbol);
            if(!list.isEmpty()){
                return list.get(0);
            } else {
                throw new StockServiceException("There is no stock data for: " + symbol);
            }
        } catch (IOException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public List<StockQuote> getQuote(String symbol, LocalDateTime from, LocalDateTime until, IntervalEnum interval) throws StockServiceException {
        // Get data from up to 5 days prior in case the start day is on a weekend or holiday
        LocalDateTime adjustedFrom = from.minusDays(5);
        String fromStr = adjustedFrom.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        String untilStr = until.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));

        String queryString = "https://api.unibit.ai/v2/stock/historical";
        queryString += "?tickers=" + symbol;
        queryString += "&startDate=" + fromStr;
        queryString += "&endDate=" + untilStr;
        queryString += "&dataType=json";
        queryString += "&accessKey=Lm0BuvDvURYy_xjVVbz3qRSRGZrQ_Cfg";

        // Set up the connection to the online service
        try {
            java.net.URL connection_url = new java.net.URL(queryString);
            InputStream stream = connection_url.openStream();

            // Stream the results
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            // Store the results in a string to return (default results are json)
            StringBuilder resultsString = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                resultsString.append(line);
                line = reader.readLine();
            }
            List<StockQuote> stockQuotes = parseJson(resultsString.toString(), symbol);
            if (stockQuotes.isEmpty()) {
                throw new StockServiceException("There is no stock data for: " + symbol
                        + " for the selected date range.");
            }
            return getListByInterval(stockQuotes, from, until, interval);
        } catch (IOException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }

    // Parses the JSON response into a list of StockQuotes sorted by date
    private List<StockQuote> parseJson(String json, String symbol) throws StockServiceException {
        List<StockQuote> quotes = new ArrayList<>();
        try {
            JSONObject topLevel = new JSONObject(json);
//        System.out.println(topLevel.toString(4));
            JSONObject resultData = topLevel.getJSONObject("result_data");
            JSONArray jsonArray = resultData.getJSONArray(symbol);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dayData = jsonArray.getJSONObject(i);
                BigDecimal price = BigDecimal.valueOf(dayData.getDouble("close"));
                String day = dayData.getString("date") + " 00:00:00";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime time = LocalDateTime.parse(day, formatter);
                StockQuote quote = new StockQuote(symbol, price, time);
                quotes.add(quote);
            }
        } catch (JSONException exception){
            throw new StockServiceException("There is no stock data for: " + symbol);
        }

        // Sort by date
        quotes.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        return quotes;
    }
}
