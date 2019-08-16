package com.github.mistra.graphqldemo.persistenza;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.mistra.graphqldemo.model.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseHelper {

    private Book StringToBook(String s) {
        String[] data = s.split(";");
        return new Book(data[0], data[1], data[2], data[3]);
    }

    private Author StringToAuthor(String s) {
        String[] data = s.split(";");
        return new Author(data[0], data[1]);
    }

    public List<Book> getBooks(String booksPath) throws IOException {
        log.debug("attempting to fetch books at path " + booksPath);
        Path path = Paths.get(booksPath);
        try(Stream<String> stream = Files.lines(path)) {
            return stream.map(this::StringToBook).collect(Collectors.toList());
        }
    }

    public List<Author> getAuthors(String authorsPath) throws IOException {
        log.debug("attempting to fetch authors at path " + authorsPath);
        Path path = Paths.get(authorsPath);
        try(Stream<String> stream = Files.lines(path)) {
            return stream.map(this::StringToAuthor).collect(Collectors.toList());
        }
    }
}