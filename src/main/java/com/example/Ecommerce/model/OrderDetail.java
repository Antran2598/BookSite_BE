package com.example.Ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "book_id")
    private Long book_id;

    @Min(value = 0, message = "Amount should not be less than 0")
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

}