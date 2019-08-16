package com.github.mistra.graphqldemo.resolver;

import java.io.IOException;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorMutation implements GraphQLMutationResolver {

    @Autowired
    private AuthorRepository authorRepo;

    public Author createAuthor(String name) throws IOException {
        log.debug("author to be created: " + name);
        return authorRepo.createAuthor(name);
    }

    public Author updateAuthor(String id, String name) throws IOException {
        log.debug("author to be changed: " + id + " " + name);
        return authorRepo.updateAuthor(id, name);
    }

    public String deleteAuthor(String id) throws IOException {
        log.debug("author to be delete: " + id);
        authorRepo.deleteAuthor(id);
        return id;
    }
}