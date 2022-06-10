package com.example.Ecommerce.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String userName;

    private Long status;

    private Set<String> roles = new HashSet<>();

}
