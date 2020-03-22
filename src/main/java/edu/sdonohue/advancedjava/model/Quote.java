package edu.sdonohue.advancedjava.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Models the Person table
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

    public Quote(){

    }

    public Quote(String symbol, String price, String time){
        if (symbol == null || symbol.length() == 0){
            throw new NullPointerException("Symbol cannot be null or empty");
        }
        this.symbol = symbol;

        if (price == null || price.length() == 0){
            throw new NullPointerException("price cannot be null or empty");
        }
        try {
            this.price = BigDecimal.valueOf(Double.parseDouble(price));
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
     * @return an integer value
     */
    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

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
