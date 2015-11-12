package ru.ncedu.books;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Gamzat on 06.11.2015.
 */
@XmlRootElement
public class Book {
    @XmlAttribute
    private String id;
    @XmlElement
    private String author;
    @XmlElement
    private String genre;
    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlElement(name = "publish_date")
    private Date date;
    @XmlElement(name = "price")
    private Double cost;

    public Book(){

    }

    public Book(String id, String author, String genre, String title, String description, Date date, Double cost) {
        this.id = id;
        this.author = author;
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.date = date;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Double getCost() {
        return cost;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!id.equals(book.id)) return false;
        if (!author.equals(book.author)) return false;
        if (!genre.equals(book.genre)) return false;
        if (!title.equals(book.title)) return false;
        if (!description.equals(book.description)) return false;
        if (!date.equals(book.date)) return false;
        return cost.equals(book.cost);

    }

    @Override
    public int hashCode() {
        return 0;
    }
}
