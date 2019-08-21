package com.github.mistra.graphqldemo.persistenza;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.mistra.graphqldemo.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Component
@ConfigurationProperties(prefix="dbfiles")
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

    @Autowired
    DatabaseHelper(DbConfiguration dbConf) throws IOException{
        this.booksPath = Paths.get(dbConf.getBooksPath());
        this.authorsPath = Paths.get(dbConf.getAuthorsPath());

        try (Stream<String> stream = Files.lines(authorsPath)) {
            AUTHOR_COUNTER = stream.map(this::lineToId).reduce((a, b) -> b).orElse("0");
        }
    }

    public List<Book> getBooks() throws IOException {
        log.debug("attempting to fetch books at path " + booksPath.toString());
        try (Stream<String> stream = Files.lines(booksPath)) {
            return stream.map(this::StringToBook).collect(Collectors.toList());
        }
    }

    public List<Author> getAuthors() throws IOException {
        log.debug("attempting to fetch authors at path " + authorsPath.toString());
        try (Stream<String> stream = Files.lines(authorsPath)) {
            return stream.map(this::StringToAuthor).collect(Collectors.toList());
        }
    }

    public Author createAuthor(String name) throws IOException {
        try (Stream<String> stream = Files.lines(authorsPath)) {
            List<String> lines = stream.collect(Collectors.toList());
            Author a = new Author(getAuthorCounter(), name);
            lines.add(AuthorToString(a));
            Files.write(authorsPath, lines);
            return a;
        }
    }

    public void updateAuthor(Author a) throws IOException {
        Function<String, String> swap = swapProperString(a, this::AuthorToString);
        try (Stream<String> stream = Files.lines(authorsPath)) {
            List<String> lines = stream.map(swap).collect(Collectors.toList());
            Files.write(authorsPath, lines);
        }
    }

    public void deleteAuthor(String id) throws IOException {
        try (Stream<String> stream = Files.lines(authorsPath)) {
            List<String> lines = stream.filter(s -> !s.startsWith(id)).collect(Collectors.toList());
            Files.write(authorsPath, lines);
        }
    }

    // Helper functions

    private String getAuthorCounter() {
        int counter = Integer.parseInt(AUTHOR_COUNTER);
        AUTHOR_COUNTER = String.valueOf(counter + 1);
        return AUTHOR_COUNTER;
    }

    private String lineToId(String s) {
        String[] data = s.split(";");
        return data[0];
    }

    private Book StringToBook(String s) {
        String[] data = s.split(";");
        return new Book(data[0], data[1], data[2], data[3]);
    }

    private String AuthorToString(Author a) {
        return String.join(";", a.getId(), a.getName());
    }

    private Author StringToAuthor(String s) {
        String[] data = s.split(";");
        return new Author(data[0], data[1]);
    }

    private <T extends Indexable> Function<String, String> swapProperString(T t, Function<T, String> predicate) {
        return str -> str.startsWith(t.getId()) ? predicate.apply(t) : str;
    }
}