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
    public List<StockQuote> getQuote(@NotNull String symbol, @NotNull Calendar from, @NotNull Calendar until,
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            statement.setString(2, dateFormat.format(from.getTime()));
            statement.setString(3, symbol);
            statement.setString(4, dateFormat.format(from.getTime()));
            statement.setString(5, dateFormat.format(until.getTime()));
            ResultSet resultSet = statement.executeQuery();
            List<StockQuote> stockQuotes = new ArrayList<>(resultSet.getFetchSize());
            while(resultSet.next()) {
                Date date = resultSet.getDate("time");
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.setTime(date);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
                BigDecimal price = resultSet.getBigDecimal("price");
                stockQuotes.add(new StockQuote(symbol, price, localDateTime));
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

    //Takes a list of raw quotes from the source and creates a new list of quotes, one for each interval
    private List<StockQuote> getListByInterval(List<StockQuote> rawQuotes, Calendar from, Calendar until, IntervalEnum interval){
        List<StockQuote> listByInterval = new ArrayList<>();
        StockQuote previousRecord = null;
        StockQuote currentRecord = rawQuotes.get(0);
        int index = 0;
        for (Calendar timeOfQuote = (Calendar)from.clone(); !timeOfQuote.after(until); interval.advance(timeOfQuote)){
            while (currentRecord != null && currentRecord.getDateAsCalendar().compareTo(timeOfQuote) <= 0){
                previousRecord = currentRecord;
                //check if end of list
                index++;
                if (index < rawQuotes.size()){
                    currentRecord = rawQuotes.get(index);
                } else {
                    currentRecord = null;
                }
            }
            listByInterval.add(new StockQuote(previousRecord.getCompanySymbol(), previousRecord.getPrice(),
                    asLocalDateTime(timeOfQuote)));
        }

        return listByInterval;
    }

    //converts Calendar to LocalDateTime
    private LocalDateTime asLocalDateTime(@NotNull Calendar calendar){
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
