package ru.ncedu.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gamzat on 03.12.2015.
 */
@Entity(name = "authors")
@NamedQueries({
        @NamedQuery(name = "Author.getAuthors", query = "SELECT a FROM authors a"),
        @NamedQuery(name = "Author.getAuthorById", query = "SELECT a FROM authors a WHERE a.id = :id")}
)
public class Author extends ru.ncedu.bean.Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    Integer age;

    @OneToMany(targetEntity = Book.class)
    List<Book> books;


    public Author() {
    }

    public Author(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", books=" + books +
                '}';
    }
}
