package edu.sdonohue.advancedjava.model;

import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Class for accessing and manipulating the 'quotes' database table.
 *
 * @author Sean Donohue
 */
public class QuotesDao {

    /**
     * Get a list of all the Quotes in the database.
     *
     * @return The list of all quotes.
     */
    public static List<Quote> getQuoteList(){
        Session session = null;
        List<Quote> quotes = null;
        try {
            session = DatabaseUtils.getSessionFactory().openSession();
            String queryString = "From Quote";
            Query query = session.createQuery(queryString);
            quotes = (List<Quote>) query.list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return quotes;
    }

    /**
     * Get the Quote instance that has the given id in the database.
     *
     * @param id The id to find in the database
     * @return The Quote with that id
     */
    public static Quote getQuoteById(int id){
        Session session = null;
        Quote quote = null;
        try {
            session = DatabaseUtils.getSessionFactory().openSession();
            String queryString = "From Quote";
            Query query = session.createQuery(queryString);
            quote = (Quote) session.get(Quote.class, id);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return quote;
    }

    /**
     * Adds a new quote to the database, or updates an existing quote.
     *
     * @param quote The quote to add or update
     */
    public static void updateOrInsertQuote(Quote quote){
        Session session = null;
        Transaction transaction = null;
        try {
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(quote);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    /**
     * Deletes a quote from the database.
     *
     * @param quote The quote to delete
     */
    public static void deleteQuote(Quote quote){
        Session session = null;
        Transaction transaction = null;
        try {
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(quote);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
