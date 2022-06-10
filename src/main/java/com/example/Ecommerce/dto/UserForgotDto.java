package com.example.Ecommerce.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserForgotDto {
    @NotBlank(message = "Username not blank")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    String userName;
}
