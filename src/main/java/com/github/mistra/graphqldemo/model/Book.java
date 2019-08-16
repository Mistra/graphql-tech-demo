package com.github.mistra.graphqldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Indexable {

    private String id;
    
    private String title;

    private String description;

    private String authorId;
}