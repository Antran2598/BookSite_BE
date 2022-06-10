package com.example.Ecommerce.payload.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SignUpRequest {

    @Column(length = 45, nullable = false)
    private String username;

    @Column(length = 45, nullable = false)
    private String password;

    private Set<String> role;


}