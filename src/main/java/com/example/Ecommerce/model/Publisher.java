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
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank(message = "pub_name is mandatory")
    @Size(min = 5, max = 45, message = "pub_name must be between 5 and 100 characters")
    @Column(name = "pub_name")
    private String pubName;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books = new LinkedHashSet<>();

}