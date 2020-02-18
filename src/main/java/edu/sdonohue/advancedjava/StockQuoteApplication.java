package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.StockService.IntervalEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * An application that retrieves StockQuotes for a given company.
 *
 * @author Sean Donohue
 * @version 1.2
 */
public class StockQuoteApplication {

    /**
     * The main method for StockQuoteApplication that returns the requested StockQuotes.
     * The first parameter is required and is the stock market symbol of the company the
     * quote(s) will be for. (e.g. APPL) The second and third optional parameters are the
     * start and end dates of a date range in the format MM/dd/yyyy (e.g. 12/31/2019).
     * A StockQuote will be returned for each date in the specified range, inclusive of
     * the start and end dates.
     *
     * @param args Required: company symbol (e.g. APPL),
     *             Optional: start date, end date (e.g. 01/01/2019 12/31/2019)
     */
    public static void main(String[] args) {
        //Make sure we have the right number of arguments
        if (args == null || args.length == 0) {
            throw new NullPointerException("No arguments. Company symbol is required.");
        } else if (args.length == 2){
            throw new NullPointerException("If the start date is included, the end date is required");
        } else if (args.length > 3){
            throw new IllegalArgumentException("Too many arguments.");
        }

        String symbol = args[0];
        if (symbol == null || symbol.isEmpty()){
            throw new NullPointerException("Company symbol is null or blank.");
        }

        //We know we have either 1 or 3 arguments
        if (args.length == 3){
            Calendar from = getCalendar(args[1]);
            Calendar until = getCalendar(args[2]);
            if (from.after(until)){
                throw new IllegalArgumentException("The start date must be before the end date.");
            }
            if (until.after(Calendar.getInstance())){
                throw new IllegalArgumentException("The end date must not be after today.");
            }
            getQuotes(symbol, from, until, IntervalEnum.DAILY);
        } else {
            getQuote(symbol);
        }
    }

    //Utility method for turning a date string argument into a Calendar instance.
    private static Calendar getCalendar(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Argument is not a valid date", e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Output the StockQuote for today for the given company
    private static void getQuote(String symbol){
        StockQuote quote = StockServiceFactory.getStockService().getQuote(symbol);
        System.out.println(quote);
    }

    //Output the list of StockQuotes for the given company within the given date range.
    private static void getQuotes(String symbol, Calendar from, Calendar until, IntervalEnum interval){
        List<StockQuote> quotes = StockServiceFactory.getStockService().getQuote(symbol, from, until, interval);
        for (StockQuote quote : quotes){
            System.out.println(quote);
        }
    }

}
