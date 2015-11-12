package ru.ncedu.books.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.ncedu.books.Book;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gamzat on 06.11.2015.
 */
public class BookDomParser implements DefaultParser {
    private static String BOOK_TAG_NAME = "book";
    private static String AUTHOR_TAG_NAME = "author";
    private static String GENRE_TAG_NAME = "genre";
    private static String TITLE_TAG_NAME = "title";
    private static String DESCRIPTION_TAG_NAME = "description";
    private static String DATE_TAG_NAME = "publish_date";
    private static String COST_TAG_NAME = "price";

    private static String ID_ATTRIBUTE_NAME = "id";

    private String filename;

    public BookDomParser(String filename) {
        this.filename = filename;
    }

    public List<Book> getBooks() throws Exception {
        List<Book> result = new ArrayList<Book>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(filename));

        NodeList books = doc.getElementsByTagName(BOOK_TAG_NAME);
        for (int i = 0; i < books.getLength(); ++i) {
            Element book = (Element) books.item(i);
            String author = book.getElementsByTagName(AUTHOR_TAG_NAME).item(0).getTextContent();
            String genre = book.getElementsByTagName(GENRE_TAG_NAME).item(0).getTextContent();
            String title = book.getElementsByTagName(TITLE_TAG_NAME).item(0).getTextContent();
            String description = book.getElementsByTagName(DESCRIPTION_TAG_NAME).item(0).getTextContent();
            String id = book.getAttribute(ID_ATTRIBUTE_NAME);

            Double cost = Double.parseDouble(book.getElementsByTagName(COST_TAG_NAME).item(0).getTextContent());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(book.getElementsByTagName(DATE_TAG_NAME).item(0).getTextContent());

            result.add(new Book(id,author, genre, title, description, date, cost));
        }
        return result;
    }
}
