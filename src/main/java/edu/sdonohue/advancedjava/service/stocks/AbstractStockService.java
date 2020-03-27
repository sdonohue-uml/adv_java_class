package edu.sdonohue.advancedjava.service.stocks;

import edu.sdonohue.advancedjava.model.StockQuote;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStockService implements StockService {

    //Takes a list of raw quotes from the source and creates a new list of quotes, one for each interval
    protected List<StockQuote> getListByInterval(List<StockQuote> rawQuotes, LocalDateTime from, LocalDateTime until, IntervalEnum interval){
        List<StockQuote> listByInterval = new ArrayList<>();
        StockQuote previousRecord = null;
        StockQuote currentRecord = rawQuotes.get(0);
        int rawQuotesIndex = 0;
        for (LocalDateTime timeOfQuote = from.plusDays(0); !timeOfQuote.isAfter(until); timeOfQuote = interval.advance(timeOfQuote)){
            while (currentRecord != null && currentRecord.getDate().compareTo(timeOfQuote) <= 0){
                previousRecord = currentRecord;
                //check if end of list
                rawQuotesIndex++;
                if (rawQuotesIndex < rawQuotes.size()){
                    currentRecord = rawQuotes.get(rawQuotesIndex);
                } else {
                    currentRecord = null;
                }
            }
            if (previousRecord != null){
                listByInterval.add(new StockQuote(previousRecord.getCompanySymbol(), previousRecord.getPrice(),timeOfQuote));
            }
        }

        return listByInterval;
    }
}
