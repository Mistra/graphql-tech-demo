package com.github.mistra.graphqldemo.repository;

import java.util.List;

import com.github.mistra.graphqldemo.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    public List<Book> findByTitle(String title);

    public List<Book> findByAuthorId(Long authorId);
    
}