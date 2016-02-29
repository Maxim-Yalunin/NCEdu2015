package ru.ncedu.entity;

import javax.persistence.*;

/**
 * Created by Gamzat on 03.12.2015.
 */
@Entity(name = "books")
@NamedQueries({
    @NamedQuery(name = "Book.getBooks", query = "SELECT b FROM books b"),
    @NamedQuery(name = "Book.getByAuthor", query = "SELECT b from books b where b.author = : author"),
    @NamedQuery(name = "Book.getByYear", query = "SELECT b from books b where b.year = : year"),
    @NamedQuery(name = "Book.getByAuthorAndYear", query = "SELECT b from books b where " +
            "b.author = : author and b.year = : year")}
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String name;
    @Column
    String description;
    @Column
    String genre;
    @Column
    Integer year;

    @ManyToOne
    @JoinColumn
    Author author;

    public Book() {
    }

    public Book(String name, String description, String genre, Integer year, Author author) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }
}
