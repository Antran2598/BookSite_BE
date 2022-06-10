package com.example.Ecommerce.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RemoveCartItemDto {
    private Long userId;

    private Long itemId;
}
