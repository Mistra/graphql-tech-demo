package com.github.mistra.graphqldemo.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.github.mistra.graphqldemo.model.Book;
import com.github.mistra.graphqldemo.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookMutation implements GraphQLMutationResolver {

    @Autowired
    private BookRepository bookRepo;

    public Book createBook(Long authorId, String title, String description) {
        log.debug("book to be created: " + title);
        Book book = new Book(authorId, title, description);
        bookRepo.save(book);
        log.debug("book created with id: " + book.getId());
        return book;
    }

    public Boolean deleteBook(Long id) {

        log.debug("book to be delete: " + id);
        
        bookRepo.deleteById(id);
        if (bookRepo.findById(id).isPresent()) {
            bookRepo.deleteById(id);
            return true;
        }

        return false;
    }
}