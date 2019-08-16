package com.github.mistra.graphqldemo.repository;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.persistenza.DatabaseHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorRepository {
    //TODO: spostare in database
    @Value("${dbfiles.authorspath}")
    String authorPath;
    
    //TODO: scoprire perchè autowire non funziona
    private DatabaseHelper dbHelper = new DatabaseHelper();

    private Map<String, Author> authors;

    public AuthorRepository() throws IOException {
        log.debug("authors path: " + authorPath);
        //TODO: mettere a posto autoconfig
        authorPath = "authors.txt";
        authors = dbHelper.getAuthors(authorPath).stream().collect(Collectors.toMap(Author::getId, a -> a));
    }

    public Author getById(String id) {
        return authors.get(id);
    }
}