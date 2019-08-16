package com.github.mistra.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.mistra.graphqldemo.model.*;
import com.github.mistra.graphqldemo.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookResolver implements GraphQLResolver<Book> {
    @Autowired
    AuthorRepository authorRepo;

    public Author getAuthor(Book book) {
        log.debug("getting infos of author");
        return authorRepo.getById(book.getAuthorId());
    }
}