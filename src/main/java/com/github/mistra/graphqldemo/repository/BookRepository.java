package com.github.mistra.graphqldemo.repository;

import java.util.HashMap;
import java.util.Map;

import com.github.mistra.graphqldemo.model.Book;

import org.springframework.stereotype.Component;

@Component
public class BookRepository {

    private Map<String, Book> books;

    public BookRepository() {
        Book b1 = new Book("1", "Il Signore degli Anelli");
        Book b2 = new Book("2", "Lo Hobbit");

        books = new HashMap<>();
        books.put("1", b1);
        books.put("2", b2);
    }

    public Book getById(String id) {
        return books.get(id);
    }
}