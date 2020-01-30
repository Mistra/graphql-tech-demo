package com.github.mistra.graphqldemo.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorResolver implements GraphQLResolver<Author> {
    @Autowired
    BookRepository bookRepo;

    public List<Book> getBooks(Author author) {
        log.debug("getting all books of author");
        return bookRepo.findByAuthorId(author.getId());
    }
}