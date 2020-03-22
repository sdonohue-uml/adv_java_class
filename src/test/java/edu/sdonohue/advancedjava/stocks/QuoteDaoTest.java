package edu.sdonohue.advancedjava.stocks;

import edu.sdonohue.advancedjava.model.Quote;
import edu.sdonohue.advancedjava.model.QuotesDao;
import edu.sdonohue.advancedjava.util.DatabaseInitializationException;
import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuoteDaoTest {

    @Before
    @After
    public void initDb(){
        try {
            DatabaseUtils.initializeDatabase(null);
        } catch (DatabaseInitializationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuoteList(){
        List<Quote> quotes = QuotesDao.getQuoteList();
        for (Quote quote : quotes){
            System.out.println(quote.toString());
        }
        assertEquals("Number of quotes should be 10", 10, quotes.size());
    }

    @Test
    public void testInsertQuote(){
        Quote newQuote = new Quote("TEST", "100", "2015-02-10 00:00:01");
        QuotesDao.updateOrInsertQuote(newQuote);
        List<Quote> quotes = QuotesDao.getQuoteList();
        for (Quote quote : quotes){
            System.out.println(quote.toString());
        }
        assertEquals("Number of quotes should be 11", 11, quotes.size());
    }

    @Test
    public void testGetQuoteById(){
        Quote quote = QuotesDao.getQuoteById(1);
        assertEquals("Quote symbol should be GOOG", "GOOG", quote.getSymbol());
        assertEquals("Quote time should be 2004-08-19 00:00:01",
                Timestamp.valueOf("2004-08-19 00:00:01"), quote.getTime());
        assertEquals("Quote price should be 85", new BigDecimal(85), quote.getPrice());
    }

    @Test
    public void testDeleteQuote(){
        Quote delQuote = QuotesDao.getQuoteById(1);
        QuotesDao.deleteQuote(delQuote);
        List<Quote> quotes = QuotesDao.getQuoteList();
        for (Quote quote : quotes){
            System.out.println(quote.toString());
        }
        assertEquals("Number of quotes should be 9", 9, quotes.size());
    }


}
