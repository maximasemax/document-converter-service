package com.maximase.document_converter_service.controller;

import com.maximase.document_converter_service.entity.Role;
import com.maximase.document_converter_service.entity.User;
import com.maximase.document_converter_service.repository.RoleRepository;
import com.maximase.document_converter_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {


    public record RegisterDto(String username, String password) {}

    @GetMapping("/me")
    public String me(Authentication auth) {
        return "Hello, " + auth.getName();
    }
}
