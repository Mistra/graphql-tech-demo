package com.github.mistra.graphqldemo.persistenza;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.mistra.graphqldemo.exception.ParsingRuntimeException;
import com.github.mistra.graphqldemo.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@ConfigurationProperties(prefix = "dbfiles")
class DbConfiguration {
    private String booksPath;
    private String authorsPath;
}

@Slf4j
@Component
public class DatabaseHelper {

    private static String AUTHOR_COUNTER;
    private Path booksPath;
    private Path authorsPath;

    private Stream<String> readFile(Path path) {
        log.debug("attempting to read resource at " + path.toString());
        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.toList()).stream();
        } catch (IOException e) {
            throw new ParsingRuntimeException("error reading file: " + path.toString(), e);
        }
    }

    private void writeFile(Path path, Stream<String> stream) {
        log.debug("attempting to write resource at " + path.toString());
        try {
            Files.write(path, stream.collect(Collectors.toList()));
        } catch (IOException e) {
            throw new ParsingRuntimeException("error writing file: " + path.toString(), e);
        }
    }

    @Autowired
    DatabaseHelper(DbConfiguration dbConf) throws IOException {
        this.booksPath = Paths.get(dbConf.getBooksPath());
        this.authorsPath = Paths.get(dbConf.getAuthorsPath());

        try (Stream<String> stream = Files.lines(authorsPath)) {
            AUTHOR_COUNTER = stream.map(this::lineToId).reduce((a, b) -> b).orElse("0");
        }
    }

    public List<Book> getBooks() {
        return readFile(booksPath).map(this::stringToBook).collect(Collectors.toList());
    }

    public List<Author> getAuthors() {
        return readFile(authorsPath).map(this::stringToAuthor).collect(Collectors.toList());
    }

    public Author createAuthor(String name) {
        Author author = new Author(getAuthorCounter(), name);
        Stream<String> stream = readFile(authorsPath);
        writeFile(authorsPath, Stream.concat(stream, Stream.of(authorToString(author))));
        return author;
    }

    public void updateAuthor(Author a) {
        Function<String, String> swap = swapProperString(a, this::authorToString);
        writeFile(authorsPath, readFile(authorsPath).map(swap));
    }

    public void deleteAuthor(String id) {
        writeFile(authorsPath, readFile(authorsPath).filter(s -> !id.equals(lineToId(s))));
    }

    // Helper functions

    String getAuthorCounter() {
        int counter = Integer.parseInt(AUTHOR_COUNTER);
        AUTHOR_COUNTER = String.valueOf(counter + 1);
        return AUTHOR_COUNTER;
    }

    String lineToId(String s) {
        String[] data = s.split(";");
        return data[0];
    }

    Book stringToBook(String s) {
        String[] data = s.split(";");
        return new Book(data[0], data[1], data[2], data[3]);
    }

    String authorToString(Author a) {
        return String.join(";", a.getId(), a.getName());
    }

    Author stringToAuthor(String s) {
        String[] data = s.split(";");
        return new Author(data[0], data[1]);
    }

    <T extends Indexable> Function<String, String> swapProperString(T t, Function<T, String> predicate) {
        return str -> str.startsWith(t.getId()) ? predicate.apply(t) : str;
    }
}