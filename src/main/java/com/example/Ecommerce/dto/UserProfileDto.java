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
public class UserProfileDto {
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String userName;

    @Size(min = 5, max = 100, message = "Password must be between 10 and 100 characters")
    private String password;

    private Long status;

    private Set<RoleDto> roles = new HashSet<>();
}
