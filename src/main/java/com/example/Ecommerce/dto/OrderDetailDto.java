package com.example.Ecommerce.dto;

import lombok.*;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class OrderDetailDto {

    private Long id;

    private Long orderId;

    private Long itemId;;

    @Min(value = 0, message = "Amount should not be less than 0")
    private int amount;

    private Double price;

}
