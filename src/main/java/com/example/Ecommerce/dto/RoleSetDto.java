package com.example.Ecommerce.dto;

import java.util.HashSet;
import java.util.Set;

public class RoleSetDto {
    private Long userId;

    private Set<RoleDto> roles = new HashSet<>();
}
