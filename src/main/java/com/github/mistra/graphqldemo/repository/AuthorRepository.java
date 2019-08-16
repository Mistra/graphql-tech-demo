package com.github.mistra.graphqldemo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.mistra.graphqldemo.model.Author;
import com.github.mistra.graphqldemo.persistenza.DatabaseHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorRepository {
    // TODO: spostare in database
    @Value("${dbfiles.authorspath}")
    String authorPath;

    // TODO: scoprire perchè autowire non funziona
    private DatabaseHelper dbHelper = new DatabaseHelper();

    private Map<String, Author> authors;

    public AuthorRepository() throws IOException {
        log.debug("authors path: " + authorPath);
        // TODO: mettere a posto autoconfig
        authorPath = "authors.txt";
        authors = dbHelper.getAuthors(authorPath).stream().collect(Collectors.toMap(Author::getId, a -> a));
    }

    public Author getById(String id) {
        return authors.get(id);
    }

    public List<Author> getAll() {
        return new ArrayList<Author>(authors.values());
    }

    public Author createAuthor(String name) throws IOException {
        String id = UUID.randomUUID().toString();
        Author author = new Author(id, name);
        // TODO: mettere a posto autoconfig
        authorPath = "authors.txt";
        dbHelper.createAuthor(author, authorPath);
        authors.put(id, author);
        return author;
    }

    public Author updateAuthor(String id, String name) throws IOException {
        Author author = getById(id);
        if (name == null)
            return author;
        author.setName(name);
        // TODO: mettere a posto autoconfig
        authorPath = "authors.txt";
        dbHelper.updateAuthor(author, authorPath);
        return author;
    }

    public void deleteAuthor(String id) throws IOException {
        authors.remove(id);
        // TODO: mettere a posto autoconfig
        authorPath = "authors.txt";
        dbHelper.deleteAuthor(id, authorPath);
    }
}