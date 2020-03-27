package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;
import edu.sdonohue.advancedjava.service.stocks.StockService;
import edu.sdonohue.advancedjava.service.stocks.StockServiceException;
import edu.sdonohue.advancedjava.util.DatabaseConnectionException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An implementation of the StockService interface that gets
 * stock data from a database.
 */
public class DatabaseStockService extends AbstractStockService {

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public StockQuote getQuote(@NotNull String symbol) throws StockServiceException {
        if (symbol == null){
            throw new NullPointerException("Compnay symbol must not be null");
        }
        try {
            Connection connection = DatabaseUtils.getConnection();
            String queryString = new StringBuilder()
                    .append("SELECT * FROM stocks.quotes ")
                    .append("WHERE symbol like ? ")
                    .append("ORDER BY time DESC ")
                    .append("LIMIT 1")
                    .toString();
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
            }
            if (stockQuotes.isEmpty()) {
                throw new StockServiceException("There is no stock data for: " + symbol);
            }
            return stockQuotes.get(0);
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    @NotNull
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull LocalDateTime from, @NotNull LocalDateTime until,
                                     @NotNull IntervalEnum interval) throws StockServiceException {
        try {
            Connection connection = DatabaseUtils.getConnection();
            String queryString = new StringBuilder()
                    .append("(SELECT * FROM stocks.quotes ")
                    .append("WHERE symbol like ? and time <= ? ")
                    .append("ORDER BY time DESC ")
                    .append("LIMIT 1) ")
                    .append("UNION ")
                    .append("(SELECT * FROM stocks.quotes ")
                    .append("WHERE symbol like ? and time >= ? and time <= ?) ")
                    .append("ORDER BY time ASC")
                    .toString();
            PreparedStatement statement = connection.prepareStatement(queryString);
            statement.setString(1, symbol);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(2, from.format(formatter));
            statement.setString(3, symbol);
            statement.setString(4, from.format(formatter));
            statement.setString(5, until.format(formatter));
            ResultSet resultSet = statement.executeQuery();
            List<StockQuote> stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                LocalDateTime time = resultSet.getTimestamp("time").toLocalDateTime();
//                Date date = resultSet.getDate("time");
//                Calendar calendar = Calendar.getInstance();
//                calendar.clear();
//                calendar.setTime(date);
//                LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price, time));
            }
            if (stockQuotes.isEmpty()) {
                throw new StockServiceException("There is no stock data for: " + symbol
                        + " for the selected date range.");
            }
            return getListByInterval(stockQuotes, from, until, interval);
        } catch (DatabaseConnectionException | SQLException exception) {
            throw new StockServiceException(exception.getMessage(), exception);
        }
    }
}
