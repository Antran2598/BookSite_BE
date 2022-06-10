package com.example.Ecommerce.dto;

import com.example.Ecommerce.model.Author;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.model.Publisher;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class BookDto {
     Long id;
     Long author;
     String bookName;
     Double originalPrice;
     String picture;
     Long category;
     Long publisher;
     String description;
     Double salePrice;
     Integer quantity;
}
