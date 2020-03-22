package edu.sdonohue.advancedjava.model;

import edu.sdonohue.advancedjava.util.DatabaseUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class QuotesDao {

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

    public static void insertQuote(Quote quote){
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
