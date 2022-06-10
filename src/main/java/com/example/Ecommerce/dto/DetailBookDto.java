package com.example.Ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class DetailBookDto {
    Long id;
    Long authorId;
    String bookName;
    Double originalPrice;
    String picture;
    Long categoryId;
    Long publisherId;
    String description;
    Double salePrice;
    Integer quantity;
}
