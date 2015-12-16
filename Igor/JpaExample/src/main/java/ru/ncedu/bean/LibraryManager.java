package ru.ncedu.bean;

import ru.ncedu.service.LibraryService;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gamzat on 03.12.2015.
 */
@ManagedBean
@ApplicationScoped
public class LibraryManager implements Serializable {

    public LibraryManager() {
    }

    public List<Book> getAllBooks() {
        List<ru.ncedu.entity.Book> books = LibraryService.getAllBooks();

        List<Book> resultList = new ArrayList<Book>(books.size());
        for (ru.ncedu.entity.Book book : books) {
            resultList.add(new Book(book));
        }

        return resultList;
    }

    public List<Author> getAllAuthors() {
        List<ru.ncedu.entity.Author> authors = LibraryService.getAllAuthors();

        List<Author> resultList = new ArrayList<Author>(authors.size());
        for (ru.ncedu.entity.Author author : authors) {
            resultList.add(new Author(author.getId(), author.getFirstName(), author.getLastName(), author.getAge()));
        }

        return resultList;
    }

    public void addAuthor(Author author) {
        if (author == null) {
            return;
        }
        LibraryService.addAuthor(new ru.ncedu.entity.Author(author.firstName, author.lastName, author.age));
    }


    public void addBook(Book book) {
        if (book == null) {
            return;
        }

        ru.ncedu.entity.Author author = (book.getAuthor() == null) ? null : LibraryService.getAuthorById(book.getAuthor().getId());
        LibraryService.addBook(new ru.ncedu.entity.Book(book.name, book.description, book.genre, book.year, author));
    }

    public List<Book> findBooks(FindByQuery query) {
        List<ru.ncedu.entity.Book> books = query.doQuery();
        List<Book> resultBooks = new ArrayList<Book>(books.size());
        for (ru.ncedu.entity.Book book : books) {
            resultBooks.add(new Book(book));
        }
        return resultBooks;
    }

    public List<Book> findBooks(final ru.ncedu.entity.Author author) {
        FindByQuery query = new FindByQuery() {
            public List<ru.ncedu.entity.Book> doQuery() {
                return LibraryService.getBooksByAuthor(author);
            }
        };
        return findBooks(query);
    }

    public List<Book> findBooks(final int year) {
        FindByQuery query = new FindByQuery() {
            public List<ru.ncedu.entity.Book> doQuery() {
                return LibraryService.getBooksByYear(year);
            }
        };
        return findBooks(query);
    }

    public List<Book> findBooks(final ru.ncedu.entity.Author author, final int year) {
        FindByQuery query = new FindByQuery() {
            public List<ru.ncedu.entity.Book> doQuery() {
                return LibraryService.getBooksByAuthorAndYear(author, year);
            }
        };
        return findBooks(query);
    }

    private interface FindByQuery {
        List<ru.ncedu.entity.Book> doQuery();
    }
}
