package ru.ncedu.books.parser;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ncedu.books.Book;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Gamzat on 06.11.2015.
 */
public class BookParserTest {
    private static Logger LOGGER = LoggerFactory.getLogger(BookParserTest.class);

    private Book firstBookEtalon;
    @Before
    public void init() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        firstBookEtalon = new Book("bk101",
                "Gambardella, Matthew",
                "Computer",
                "XML Developer's Guide",
                "An in-depth look at creating applications\n" +
                        "            with XML.",
                df.parse("2000-10-01"),
                44.95d);
    }

    @Test
    public void firstBookTest() throws Exception {
        LOGGER.debug("Start first book test");
        BookDomParser parser = new BookDomParser("Books.xml");
        List<Book> books = parser.getBooks();
        Book first = books.get(0);
        LOGGER.debug("First book: {}", first);
        assertEquals(firstBookEtalon, first);
    }

    @Test
    public void secondBookTest() throws Exception {
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("Books.xsd"));
            schema.newValidator().validate(new DOMSource(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("Books.xml"))));
    }
}
