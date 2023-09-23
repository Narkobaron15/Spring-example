package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.account.AuthResponseDTO;
import org.example.dto.account.LoginDTO;
import org.example.dto.account.RegisterDTO;
import org.example.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping(
            value = "login",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @ModelAttribute LoginDTO DTO
    ) {
        try {
            var auth = service.login(DTO);
            return ResponseEntity.ok(auth);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping(
            value = "register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> register(
            @Valid @ModelAttribute RegisterDTO registrationRequest
    ) {
        try {
            service.register(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
