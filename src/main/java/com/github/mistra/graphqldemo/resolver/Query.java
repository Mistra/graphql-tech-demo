package com.github.mistra.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {
    @Autowired
    BookRepository bookRepo;

    public Book getBook(String id) {
        return bookRepo.getById(id);
    }
}