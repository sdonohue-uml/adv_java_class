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
    protected List<StockQuote> getListByInterval(List<StockQuote> rawQuotes, Calendar from, Calendar until, IntervalEnum interval){
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
    protected LocalDateTime asLocalDateTime(@NotNull Calendar calendar){
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
