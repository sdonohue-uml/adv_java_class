package edu.sdonohue.advancedjava.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * Models the Person table
 */
@Entity
@Table(name="person")
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private Timestamp birthDate;

    /**
     * Primary Key - Unique ID for a particular row in the person table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the person table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *Get the First Name of the Person.
     *
     * @return the person's first name
     */
    @Basic
    @Column(name = "first_name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Specify the person's first name.
     *
     * @param firstName a String value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the Person.
     *
     * @return the person's last name
     */
    @Basic
    @Column(name = "last_name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getLastName() {
        return lastName;
    }

    /**
     * Specify the person's last name.
     *
     * @param lastName a String value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the birthdate of the Person.
     *
     * @return the person's birthdate
     */
    @Basic
    @Column(name = "birth_date", nullable = false, insertable = true, updatable = true)
    public Timestamp getBirthDate() {
        return birthDate;
    }

    /**
     * Specify the person's date of birth.
     *
     * @param birthDate  the time the person was born.
     */
    public void setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (birthDate != null ? !birthDate.equals(person.birthDate) : person.birthDate != null)
            return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null)
            return false;

        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }

    /**
     * String representation of the instance including all fields.
     *
     * @return A String representation of the instance
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    /**
     * String representation of the instance that is formatted for CLI output.
     *
     * @return A formatted String representation of the instance
     */
    public String toFriendlyString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        return firstName + " " + lastName + " (DOB: " + birthDate.toLocalDateTime().format(formatter) + ")";
    }
}
