package com.example.Ecommerce.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleDto {
    private Long id;

    @NotBlank(message = "Role name is mandatory")
    private String name;
}
