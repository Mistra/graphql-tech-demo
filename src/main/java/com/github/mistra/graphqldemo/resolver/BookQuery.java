package com.github.mistra.graphqldemo.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookQuery implements GraphQLQueryResolver {
    @Autowired
    BookRepository bookRepo;

    public Book getBook(String id) {
        return bookRepo.getById(id);
    }

    public List<Book> getBooks() {
        return bookRepo.getAll();
    }
}