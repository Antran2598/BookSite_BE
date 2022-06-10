package com.example.Ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class AddCartItemDto {
    private Long userId;

    private Long itemId;

}
