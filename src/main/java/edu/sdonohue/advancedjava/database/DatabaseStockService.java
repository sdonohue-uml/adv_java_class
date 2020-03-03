package edu.sdonohue.advancedjava.database;

import edu.sdonohue.advancedjava.database.DatabaseConnectionException;
import edu.sdonohue.advancedjava.database.DatabaseUtils;
import edu.sdonohue.advancedjava.stocks.StockQuote;
import edu.sdonohue.advancedjava.stocks.StockService;
import edu.sdonohue.advancedjava.stocks.StockServiceException;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
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
     * @inheritDoc
     */
    @Override
    @Nullable
    public StockQuote getQuote(@NotNull String symbol) throws StockServiceException {
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

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
                                     @NotNull IntervalEnum interval) throws StockServiceException {
        try {
            Connection connection = DatabaseUtils.getConnection();
            String queryString = "SELECT * FROM stocks.quotes " +
                    "WHERE symbol like ? and time >= ? and time <= ? " +
                    "ORDER BY time ASC";
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, symbol);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("from = " + dateFormat.format(from.getTime()));
            System.out.println("until = " + dateFormat.format(until.getTime()));
            statement.setString(2, dateFormat.format(from.getTime()));
            statement.setString(3, dateFormat.format(until.getTime()));
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
