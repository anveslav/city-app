package ru.apashkevich.cityapp.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Value
@Builder
public class AuthResponse {
    String message;
    List<String> roles;
}
