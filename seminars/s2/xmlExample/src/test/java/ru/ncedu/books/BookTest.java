package ru.ncedu.books;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookTest {
    private static Logger LOGGER = LoggerFactory.getLogger(BookTest.class);

    Book testBook;

    @Before
    public void init() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        testBook = new Book("id1", "Gamzat", "fantasy", "Java Fantasy", "No Description", df.parse("2015-11-12"), new Double(100d));
    }

    @Test
    public void testTitle() {
        LOGGER.debug("Start testing book name");
        LOGGER.debug("Book title: {}", testBook.getTitle());
        assertTrue("Java Fantasy".equals(testBook.getTitle()));
    }

    @Test
    public void jaxbMarshall() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Book.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(testBook, new File("testBook.xml"));
    }

    @Test
    public void jaxbUnMarshall() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Book.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Book unmarshalledBook = (Book) unmarshaller.unmarshal(new File("testBook.xml"));
        assertEquals(testBook, unmarshalledBook);
    }
}
