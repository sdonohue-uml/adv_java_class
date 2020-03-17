package edu.sdonohue.advancedjava.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "user_stocks")
public class UserStocks {
    private int id;
    private Person person;
    private String stock;

    /**
     * Create a UserStocks that needs to be initialized
     */
    public UserStocks() {
        // this empty constructor is required by hibernate framework

    }

    /**
     * Create a valid UserStocks
     *
     * @param person the person to assign the hobby to
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStocks that = (UserStocks) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        result = 31 * result + stock.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PersonHobby{" +
                "id=" + id +
                ", person=" + person +
                ", stock=" + stock +
                '}';
    }
}
