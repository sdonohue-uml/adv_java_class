package edu.sdonohue.advancedjava;

import edu.sdonohue.advancedjava.stocks.StockService.IntervalEnum;
import edu.sdonohue.advancedjava.stocks.StockQuote;
import edu.sdonohue.advancedjava.stocks.StockServiceException;
import edu.sdonohue.advancedjava.stocks.StockServiceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
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
     * quote(s) will be for. (e.g. APPL)
     * The second and third optional parameters are the start and end dates of a date range
     * in the format MM/dd/yyyy (e.g. 12/31/2019). If the second argument is included, the
     * third must also be included.
     * The fourth argument is optional and should be an string representing the desired interval
     * to return quotes for within the start and end dates. Valid options are limited to:
     * "HOURLY", "DAILY", "WEEKLY", "BI_WEEKLY", "MONTHLY",
     * "BI_MONTHLY", "SEMI_ANNUALLY", "ANNUALLY", "BI_ANNUALLY".
     * If only the symbol is passed, the StockQuote for the current time is returned. If the
     * start and end dates are included, a StockQuote will be returned for each day in the range.
     * If an interval is included, a StockQuote will be returned for 12:00am on the start date
     * and for each interval after that until inclusive of 12:00am on the end date.
     *
     * @param args Required: company symbol (e.g. APPL)
     *             Optional: start date, end date (e.g. 01/01/2019 12/31/2019)
     *             Optional: interval (e.g. DAILY)
     */
    public static void main(String[] args) {
        //Make sure we have the right number of arguments
        if (args == null || args.length == 0) {
            throw new NullPointerException("No arguments. Company symbol is required.");
        } else if (args.length == 2){
            throw new NullPointerException("If the start date is included, the end date is required");
        } else if (args.length > 4){
            throw new IllegalArgumentException("Too many arguments.");
        }

        String symbol = args[0];
        if (symbol == null || symbol.isEmpty()){
            throw new NullPointerException("Company symbol is null or blank.");
        }

        //If we have 3 or 4 arguments
        if (args.length >= 3){
            Calendar from = getCalendar(args[1]);
            Calendar until = getCalendar(args[2]);
            if (from.after(until)){
                throw new IllegalArgumentException("The start date must be before the end date.");
            }
            if (until.after(Calendar.getInstance())){
                throw new IllegalArgumentException("The end date must not be after today.");
            }
            IntervalEnum interval = IntervalEnum.DAILY;
            if (args.length == 4){
                try {
                    interval = IntervalEnum.valueOf(args[3]);
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("The entered interval is invalid", e);
                }
            }
            getQuotes(symbol, from, until, interval);
        } else { //1 argument
            getQuote(symbol);
        }
    }

    //Utility method for turning a date string argument into a Calendar instance.
    private static Calendar getCalendar(String dateString) {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        try {
            date = LocalDate.parse(dateString, formatter);
            calendar.set(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException(dateString + " is not a valid date");
        }
        return calendar;
    }

    //Output the StockQuote for today for the given company
    private static void getQuote(String symbol){
        StockQuote quote = null;
        try {
            quote = StockServiceFactory.getStockService().getQuote(symbol);
        } catch (StockServiceException e) {
            e.printStackTrace();
        }
        System.out.println(quote);
    }

    //Output the list of StockQuotes for the given company within the given date range.
    private static void getQuotes(String symbol, Calendar from, Calendar until, IntervalEnum interval){
        try {
            List<StockQuote> quotes = StockServiceFactory.getStockService().getQuote(symbol, from, until, interval);
            for (StockQuote quote : quotes){
                System.out.println(quote);
            }
        } catch (StockServiceException e) {
            e.printStackTrace();
        }
    }

}
