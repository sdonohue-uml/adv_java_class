package edu.sdonohue.advancedjava.model;

import javax.persistence.*;

/**
 * Class that represents a stock that a person is interested in.
 */
@Entity
@Table(name = "user_stocks")
public class UserStocks {
    private int id;
    private Person person;
    private String stock;

    /**
     * Create a UserStocks that needs to be initialized.
     */
    public UserStocks() {
        // this empty constructor is required by hibernate framework

    }

    /**
     * Create a valid UserStocks.
     *
     * @param person the person to assign the stock to
     * @param stock  the stock to associate the person with
     */
    public UserStocks(Person person, String stock) {
        setStock(stock);
        setPerson(person);
    }

    /**
     * Primary Key - Unique ID for a particular row in the user_stocks table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the user_stocks table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the Person for this UserStocks.
     *
     * @return get the Person associated with this stock
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "ID", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Specify the Person associated with the stock.
     *
     * @param person a person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Get the stock symbol for this UserStock.
     *
     * @return get the stock associated with this Person
     */
    @Basic
    @Column(name = "stock", nullable = false, insertable = true, updatable = true, length = 256)
    public String getStock() {
        return stock;
    }

    /**
     * Specify the stock associated with the Person.
     *
     * @param stock
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStocks that = (UserStocks) o;

        if (id != that.id) return false;

        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + stock.hashCode();
        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "UserStock{" +
                "id=" + id +
                ", person=" + person +
                ", stock=" + stock +
                '}';
    }
}
