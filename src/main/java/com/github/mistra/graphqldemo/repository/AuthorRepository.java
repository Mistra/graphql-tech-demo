package com.github.mistra.graphqldemo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.persistenza.DatabaseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorRepository {

    @Autowired
    private DatabaseHelper dbHelper;

    private Map<String, Author> authors;

    @PostConstruct
    public void postConstruct() {
        log.debug("initializing Map of Authors");
        authors = dbHelper.getAuthors().collect(Collectors.toMap(Author::getId, a -> a));
    }

    public Author getById(String id) {
        return authors.get(id);
    }

    public List<Author> getAll() {
        return new ArrayList<Author>(authors.values());
    }

    public Author createAuthor(String name) {
        Author author = dbHelper.createAuthor(name);
        authors.put(author.getId(), author);
        return author;
    }

    public Author updateAuthor(String id, String name) {
        Author author = getById(id);
        if (name == null)
            return author;
        author.setName(name);
        dbHelper.updateAuthor(author);
        return author;
    }

    public void deleteAuthor(String id) {
        authors.remove(id);
        dbHelper.deleteAuthor(id);
    }
}