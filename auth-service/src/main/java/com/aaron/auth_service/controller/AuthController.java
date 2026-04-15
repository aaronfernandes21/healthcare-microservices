package com.aaron.auth_service.controller;

import com.aaron.auth_service.dto.LoginRequestDTO;
import com.aaron.auth_service.dto.LoginResponseDTO;
import com.aaron.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    //private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService ) {
        this.authService = authService;
        //this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Login Request for Token Generation")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
//        System.out.println("EMAIL: " + loginRequestDTO.getEmail());
//        System.out.println("PASSWORD: " + loginRequestDTO.getPassword());
//        System.out.println(
//                new BCryptPasswordEncoder().matches(
//                        "aaron21",
//                        "$2a$10$3LNHUX0I3gYXhC6U8zzkiOuyHvWm8qxRpNO/pdvpnbvL2w36dsvvG"
//                )
//        );
//        System.out.println(passwordEncoder.encode(loginRequestDTO.getPassword()));
        Optional<String> optionalToken = authService.authenticate(loginRequestDTO);

        if(optionalToken.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String token = optionalToken.get();
        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
    }


    @Operation(summary = "Validation of tokens")
    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader ("Authorization") String token){
        if(token == null || !token.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validate(token.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
