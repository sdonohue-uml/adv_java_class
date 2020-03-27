package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractStockService implements StockService {

    //Takes a list of raw quotes from the source and creates a new list of quotes, one for each interval
    protected List<StockQuote> getListByInterval(List<StockQuote> rawQuotes, LocalDateTime from, LocalDateTime until, IntervalEnum interval){
        List<StockQuote> listByInterval = new ArrayList<>();
        StockQuote currentRecord = rawQuotes.get(0);
        StockQuote previousRecord = currentRecord;
        int index = 0;
        for (LocalDateTime timeOfQuote = from.plusDays(0); !from.isAfter(until); timeOfQuote = interval.advance(timeOfQuote)){
            while (currentRecord != null && currentRecord.getDate().isBefore(timeOfQuote)){
                previousRecord = currentRecord;
                //check if end of list
                index++;
                if (index < rawQuotes.size()){
                    currentRecord = rawQuotes.get(index);
                } else {
                    currentRecord = null;
                }
            }
            listByInterval.add(new StockQuote(previousRecord.getCompanySymbol(), previousRecord.getPrice(),timeOfQuote));
        }

        return listByInterval;
    }
}
