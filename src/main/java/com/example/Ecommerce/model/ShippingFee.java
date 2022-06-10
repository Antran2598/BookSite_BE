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
@Table(name = "shipping_fee")
public class ShippingFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotBlank(message = "location is mandatory")
    @Size(min = 5, max = 50, message = "location must be between 5 and 100 characters")
    @Column(name = "location")
    private String location;

    @Column(name = "fee", nullable = false)
    private Double fee;



}