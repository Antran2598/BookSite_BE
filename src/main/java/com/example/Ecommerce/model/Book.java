package com.example.Ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity

@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private Author author;

    @NotBlank(message = "Book name is mandatory")
    @Size(min = 5, max = 50, message = "Book name must be between 5 and 50 characters")
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "original_price", nullable = false)
    private Double originalPrice;

    @Column(name = "picture", length = 50)
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher")
    private Publisher publisher;

    @Column(name = "description")
    private String description;

    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    @Min(value = 0, message = "Quantity should not be less than 0")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems = new LinkedHashSet<>();


}