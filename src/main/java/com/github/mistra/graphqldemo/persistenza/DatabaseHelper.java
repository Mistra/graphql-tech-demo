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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
@ConfigurationProperties(prefix="dbfiles")
public class DatabaseHelper {
    
    private String booksPath;
    private String authorsPath;

    public List<Book> getBooks() throws IOException {
        log.debug("attempting to fetch books at path " + booksPath);
        Path path = Paths.get(booksPath);
        try (Stream<String> stream = Files.lines(path)) {
            return stream.map(this::StringToBook).collect(Collectors.toList());
        }
    }

    public List<Author> getAuthors() throws IOException {
        log.debug("attempting to fetch authors at path " + authorsPath);
        Path path = Paths.get(authorsPath);
        try (Stream<String> stream = Files.lines(path)) {
            return stream.map(this::StringToAuthor).collect(Collectors.toList());
        }
    }

    public void createAuthor(Author a) throws IOException {
        Path path = Paths.get(authorsPath);
        try (Stream<String> stream = Files.lines(path)) {
            List<String> lines = stream.collect(Collectors.toList());
            lines.add(AuthorToString(a));
            Files.write(path, lines);
        }
    }

    public void updateAuthor(Author a) throws IOException {
        Function<String, String> swap = swapProperString(a, this::AuthorToString);
        Path path = Paths.get(authorsPath);
        try (Stream<String> stream = Files.lines(path)) {
            List<String> lines = stream.map(swap).collect(Collectors.toList());
            Files.write(path, lines);
        }
    }

    public void deleteAuthor(String id) throws IOException {
        Path path = Paths.get(authorsPath);
        try (Stream<String> stream = Files.lines(path)) {
            List<String> lines = stream.filter(s -> !s.startsWith(id)).collect(Collectors.toList());
            Files.write(path, lines);
        }
    }

    // Helper functions

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