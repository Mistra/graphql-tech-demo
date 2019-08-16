package com.github.mistra.graphqldemo.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.persistenza.DatabaseHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookRepository {
    @Value("${dbfiles.bookspath}")
    String bookPath;
    
    //TODO: scoprire perchè autowire non funziona
    private DatabaseHelper dbHelper = new DatabaseHelper();

    private Map<String, Book> books;

    public BookRepository() throws IOException {
        log.debug("books path: " + bookPath);
        //TODO: mettere a posto autoconfig
        bookPath = "books.txt";
        books = dbHelper.getBooks(bookPath).stream().collect(Collectors.toMap(Book::getId, b -> b));
    }

    public Book getById(String id) {
        return books.get(id);
    }

    public List<Book> getByAuthorId(String id) {
        return books.values().stream().filter(b -> b.getAuthorId().equals(id)).collect(Collectors.toList());
    }
}