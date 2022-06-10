package com.example.Ecommerce.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class CartDto {
    private Long id;

    private Long user;

    private List<CartItemDto> cartItems = new ArrayList<>();

}
