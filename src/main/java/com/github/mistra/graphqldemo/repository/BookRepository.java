package com.github.mistra.graphqldemo.repository;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.persistenza.DatabaseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookRepository {

    @Autowired
    private DatabaseHelper dbHelper;

    private Map<String, Book> books;

    @PostConstruct
    public void postConstruct() {
        log.debug("initializing Map of Books");
        books = dbHelper.getBooks().stream().collect(Collectors.toMap(Book::getId, b -> b));
    }

    public Book getById(String id) {
        return books.get(id);
    }

    public List<Book> getAll() {
        return new ArrayList<Book>(books.values());
    }

    public List<Book> getByTitleSubstring(String substring) {
        return new ArrayList<Book>(
                books.values().stream().filter(s -> s.getTitle().contains(substring)).collect(Collectors.toList()));
    }

    public List<Book> getByAuthorId(String id) {
        return books.values().stream().filter(b -> b.getAuthorId().equals(id)).collect(Collectors.toList());
    }
}