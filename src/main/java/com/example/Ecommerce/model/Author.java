package com.example.Ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank(message = "Author name is mandatory")
    @Size(min = 5, max = 50, message = "Author name must be between 5 and 50 characters")
    @Column(name = "author_name")
    private String authorName;


    @OneToMany(mappedBy = "author" )
    private Set<Book> books = new LinkedHashSet<>();

}