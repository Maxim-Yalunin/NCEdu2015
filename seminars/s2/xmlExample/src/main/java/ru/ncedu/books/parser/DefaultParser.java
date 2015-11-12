package ru.ncedu.books.parser;

import ru.ncedu.books.Book;

import java.util.List;

/**
 * Created by Gamzat on 06.11.2015.
 */
public interface DefaultParser {
    List<Book> getBooks() throws Exception;
}
