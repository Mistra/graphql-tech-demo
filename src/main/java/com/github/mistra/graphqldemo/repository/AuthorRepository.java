package com.github.mistra.graphqldemo.repository;

import com.github.mistra.graphqldemo.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {
}