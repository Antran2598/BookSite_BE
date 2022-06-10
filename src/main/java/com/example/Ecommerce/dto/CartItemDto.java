package com.example.Ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CartItemDto {
    private Long id;

    private Long itemId;

    @Min(value = 0, message = "Amount should not be less than 0")
    private Integer amount;

    private Double price;

}
