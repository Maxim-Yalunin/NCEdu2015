package ru.ncedu.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Created by Gamzat on 03.12.2015.
 */
@ManagedBean
@RequestScoped
public class Book {
    String name;
    String description;
    String genre;
    Integer year;

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

    public Book(ru.ncedu.entity.Book book) {
        this.name = book.getName();
        this.description = book.getDescription();
        this.genre = book.getGenre();
        this.year = book.getYear();
        this.author = new Author(book.getAuthor());
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }
}
