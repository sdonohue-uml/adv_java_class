package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.database.DatabaseConnectionException;
import edu.sdonohue.advancedjava.database.DatabaseUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService implements StockService {

    /**
     * Return the current price for a share of stock  for the given symbol
     *
     * @param symbol the stock symbol of the company you want a quote for.
     *               e.g. APPL for APPLE
     * @return a  <CODE>BigDecimal</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     *                               If this happens, trying the service may work, depending on the actual cause of the
     *                               error.
     */
    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        try {
            Connection connection = DatabaseUtils.getConnection();
            String queryString = "SELECT * FROM stocks.quotes " +
                    "WHERE symbol like ? " +
                    "ORDER BY time DESC " +
                    "LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, symbol);
            ResultSet resultSet = statement.executeQuery();
            List<StockQuote> stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            if (resultSet.first()) {
                Date time = resultSet.getDate("time");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price,
                        LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())));
                if (stockQuotes.isEmpty()) {
                    throw new StockServiceException("There is no stock data for:" + symbol);
                }
                return stockQuotes.get(0);
            }
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }

        return null;
    }

    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, IntervalEnum interval) throws StockServiceException {
        try {
            Connection connection = DatabaseUtils.getConnection();
            String queryString = "select * from quotes where symbol = ?";
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, symbol);
            ResultSet resultSet = statement.executeQuery();
            List<StockQuote> stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                Date time = resultSet.getDate("time");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(time);
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price,
                        LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())));
            }
            if (stockQuotes.isEmpty()) {
                throw new StockServiceException("There is no stock data for:" + symbol);
            }
            return stockQuotes;
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }


}
