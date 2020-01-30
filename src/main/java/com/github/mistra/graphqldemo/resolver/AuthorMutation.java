package com.github.mistra.graphqldemo.resolver;

import java.util.Optional;

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

    public Author createAuthor(String name) {
        log.debug("author to be created: " + name);
        Author author = new Author(name);
        authorRepo.save(author);
        log.debug("author created with id: " + author.getId());
        return author;
    }

    public Optional<Author> updateAuthor(Long id, String name) {
        Optional<Author> author = authorRepo.findById(id);
        author.ifPresent(a -> {
            a.setName(name);
            authorRepo.save(a);
            log.debug("author updated");
        });
        return author;
    }

    public Boolean deleteAuthor(Long id) {
        log.debug("author to be deleted: " + id);

        if (authorRepo.findById(id).isPresent()) {
            authorRepo.deleteById(id);
            return true;
        }
        return false;
    }
}