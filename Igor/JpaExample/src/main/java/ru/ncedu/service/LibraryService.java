package ru.ncedu.service;

import ru.ncedu.entity.Author;
import ru.ncedu.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Gamzat on 03.12.2015.
 */
public class LibraryService {
    public static EntityManager em = Persistence.createEntityManagerFactory("NCEDU").createEntityManager();

    public static Book addBook(Book book) {
        em.getTransaction().begin();
        Book result = em.merge(book);
        em.getTransaction().commit();
        return result;
    }

    public static Author addAuthor(Author author) {
        em.getTransaction().begin();
        Author result = em.merge(author);
        em.getTransaction().commit();
        return result;
    }

    public static List<Book> getAllBooks() {
        return em.createNamedQuery("Book.getBooks", Book.class).getResultList();
    }

    public static List<Author> getAllAuthors() {
        return em.createNamedQuery("Author.getAuthors", Author.class).getResultList();
    }

    public static void removeAuthor(Author author) {
        em.getTransaction().begin();
        em.remove(author);
        em.getTransaction().commit();
    }

    public static void removeBook(Book book) {
        em.getTransaction().begin();
        em.remove(book);
        em.getTransaction().commit();
    }

    public static Author getAuthorById(Integer id) {
        TypedQuery<Author> query = em.createNamedQuery("Author.getAuthorById", Author.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public static List<Book> getBooksByAuthor(Author author) {
        TypedQuery<Book> query = em.createNamedQuery("Book.getByAuthor", Book.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    public static List<Book> getBooksByYear(int year) {
        TypedQuery<Book> query = em.createNamedQuery("Book.getByYear", Book.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public static List<Book> getBooksByAuthorAndYear(Author author, int year) {
        TypedQuery<Book> query = em.createNamedQuery("Book.getByAuthorAndYear", Book.class);
        query.setParameter("author", author);
        query.setParameter("year", year);
        return query.getResultList();
    }
}
