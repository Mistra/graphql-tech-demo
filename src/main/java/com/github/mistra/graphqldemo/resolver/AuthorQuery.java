package com.github.mistra.graphqldemo.resolver;

import java.util.List;
import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorQuery implements GraphQLQueryResolver {
    @Autowired
    AuthorRepository authorRepo;

    public Optional<Author> getAuthor(Long id) {
        return authorRepo.findById(id);
    }

    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }
}