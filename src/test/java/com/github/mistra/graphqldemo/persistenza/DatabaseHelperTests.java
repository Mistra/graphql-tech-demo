package com.github.mistra.graphqldemo.persistenza;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.model.Book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseHelperTests {
    DbConfiguration dbConf;
    DatabaseHelper dbHelper;

    public DatabaseHelperTests() throws IOException {
        dbConf = new DbConfiguration();
        dbConf.setAuthorsPath("authors.txt");
        dbConf.setBooksPath("books.txt");

        dbHelper = new DatabaseHelper(dbConf);
    }

    @Test
    public void lineToIdTest() {
        String line = "0;test1;test";

        assertEquals("0", dbHelper.lineToId(line));
    }

    @Test
    public void stringToBookTest() {
        String line = "0;a;b;c";
        Book expected = new Book("0", "a", "b", "c");

        assertEquals(expected, dbHelper.stringToBook(line));
    }

    @Test
    public void authorToStringTest() {
        Author author = new Author("0", "a");
        String expected = "0;a";

        assertEquals(expected, dbHelper.authorToString(author));
    }

    @Test
    public void stringToAuthorTest() {
        String line = "0;a";
        Author expected = new Author("0", "a");

        assertEquals(expected, dbHelper.stringToAuthor(line));
    }
}
