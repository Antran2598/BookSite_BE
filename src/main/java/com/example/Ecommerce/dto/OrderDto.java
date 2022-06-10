package com.example.Ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class OrderDto {
    private Long id;

    private Long userId;

    private Long paymentMethod;

    private Long shippingFee;

    private Double totalPrice;

    private String address;
}
