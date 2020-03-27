package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
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
 * An implementation of StockService that retrieves stock data from a RESTful service.
 *
 * @author Sean Donohue
 */
public class RestStockService extends AbstractStockService {


    @Override
    public @Nullable StockQuote getQuote(@NotNull String symbol) throws StockServiceException {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<StockQuote> getQuote(String symbol, LocalDateTime from, LocalDateTime until, IntervalEnum interval) throws StockServiceException {
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
            String resultsString = "";
            String line = reader.readLine();
            while (line != null) {
                resultsString += line;
                line = reader.readLine();
            }
            return getListByInterval(parseJson(resultsString, symbol), from, until, interval);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<StockQuote>();
    }

    private List<StockQuote> parseJson(String json, String symbol){
        List<StockQuote> quotes = new ArrayList<>();
        JSONObject topLevel = new JSONObject(json);
        System.out.println(topLevel.toString(4));
        JSONObject resultData = topLevel.getJSONObject("result_data");
        JSONArray jsonArray = resultData.getJSONArray(symbol);
        for(int i = 0 ; i < jsonArray.length(); i++) {
            JSONObject dayData = jsonArray.getJSONObject(i);
            BigDecimal price = BigDecimal.valueOf(dayData.getDouble("close"));
            String day = dayData.getString("date") + " 00:00:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse(day, formatter);
            StockQuote quote = new StockQuote(symbol, price, time);
            quotes.add(quote);
        }

        quotes.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        return quotes;
    }
}
