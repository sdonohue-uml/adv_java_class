package edu.sdonohue.advancedjava.model;

import edu.sdonohue.advancedjava.xmltodb.Stocks;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Models the quotes table
 *
 * @author Sean Donohue
 */
@Entity
@Table(name="quotes")
public class Quote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "symbol", nullable = false, insertable = true, updatable = true, length = 4)
    private String symbol;

    @Basic
    @Column(name = "price", columnDefinition="decimal", precision=10, scale=0)
    private BigDecimal price;

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    private Timestamp time;

    /**
     * No-arg constructor required by Hibernate. Do not use.
     */
    public Quote(){

    }

    /**
     * Creates a Quote instance suitable for adding to the database from a Stocks.Quote instance
     * which was unmarshalled from XML.
     *
     * @param xmlQuote The Stocks.Quote to copy
     */
    public Quote(Stocks.Quote xmlQuote){
        if (xmlQuote == null){
            throw new NullPointerException("Stocks.Quote cannot be null");
        }
        this.symbol = xmlQuote.getSymbol();
        this.price = BigDecimal.valueOf(Double.parseDouble(xmlQuote.getPrice()));
        this.time = Timestamp.valueOf(xmlQuote.getTime());
    }

    /**
     * Creates a quote instance from String values. Used for testing only.
     *
     * @param symbol The symbol (e.g. "GOOG")
     * @param price The price (e.g. "100")
     * @param time The time (e.g. "2015-02-10 00:00:01")
     */
    public Quote(String symbol, String price, String time){
        if (symbol == null || symbol.length() == 0){
            throw new NullPointerException("Symbol cannot be null or empty");
        }
        this.symbol = symbol;

        if (price == null || price.length() == 0){
            throw new NullPointerException("price cannot be null or empty");
        }
        try {
            this.price = BigDecimal.valueOf(Integer.parseInt(price));
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Price is not a valid value", e);
        }

        if (time == null || time.length() == 0){
            throw new NullPointerException("Time cannot be null or empty");
        }
        try {
            this.time = Timestamp.valueOf(time);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Time is not a valid timestamp", e);
        }
    }


    /**
     * Primary Key - Unique ID for a particular row in the quotes table.
     *
     * @return The id of the quote
     */
    public int getId() {
        return id;
    }

    /**
     * The stock symbol of the quote.
     *
     * @return The stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the stock symbol for the quote.
     *
     * @param symbol The new stock symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * The price of the quote as a BigDecimal.
     *
     * @return The price of the quote
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the quote.
     *
     * @param price The new price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * The time of the quote as a Timestamp instance.
     *
     * @return The time of the quote
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Sets the time of the quote.
     *
     * @param time The new time
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * Get a string representation of the Quote for output
     * or troubleshooting. Contains the company symbol, the date,
     * and the price.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Quote for ");
        stringBuilder.append(getSymbol());
        stringBuilder.append(" on ");
        stringBuilder.append(getTime());
        stringBuilder.append(" = $");
        stringBuilder.append(getPrice());
        return stringBuilder.toString();
    }
}
