package com.github.mistra.graphqldemo.resolver;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookQuery implements GraphQLQueryResolver {
    @Autowired
    BookRepository bookRepo;

    public Optional<Book> getBook(Long id) {
        return bookRepo.findById(id);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepo.findByTitle(title);
    }

    public List<Book> getBooks() {
        return bookRepo.findAll();
    }
}