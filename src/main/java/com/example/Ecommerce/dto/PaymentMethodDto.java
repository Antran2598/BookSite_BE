package com.example.Ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@EqualsAndHashCode
public class PaymentMethodDto {
    Long id;
    String method;
}
