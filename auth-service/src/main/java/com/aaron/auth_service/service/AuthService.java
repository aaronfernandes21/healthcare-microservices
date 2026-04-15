package com.aaron.auth_service.service;

import com.aaron.auth_service.dto.LoginRequestDTO;
import com.aaron.auth_service.models.User;
import com.aaron.auth_service.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private  final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {



            Optional<User> userOpt = userService.findByEmail(loginRequestDTO.getEmail());

            if (userOpt.isEmpty()) {
                System.out.println("USER NOT FOUND");
                return Optional.empty();
            }

            User u = userOpt.get();

            System.out.println("USER FOUND: " + u.getEmail());
            //System.out.println("DB PASSWORD: " + u.getPassword());

            boolean match = passwordEncoder.matches(
                    loginRequestDTO.getPassword(),
                    u.getPassword()
            );

            System.out.println("MATCH RESULT: " + match);

            if (!match) {
                return Optional.empty();
            }

            String token = jwtUtil.generateToken(u.getEmail(), u.getRole());
            return Optional.of(token);
        }

    public boolean validate(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;

        }catch(JwtException jwtException){
            return false;
        }
    }


//        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
//
//                .filter(u->passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
//                .map(u->jwtUtil.generateToken(u.getEmail(), u.getRole()));
//        return token;
    }

