package com.example.Ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "cate_name", nullable = false, length = 50)
    private String cateName;

    @OneToMany(mappedBy = "category")
    private Set<Book> books = new LinkedHashSet<>();


}