package com.example.Ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_method")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "shipping_fee")
    private ShippingFee shippingFee;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> listOrderDetail  =  new ArrayList<>();

}