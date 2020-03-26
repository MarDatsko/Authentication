package com.example.authentication.controller;

import com.example.authentication.security.JwtAuthenticationRequest;
import com.example.authentication.security.JwtAuthenticationResponse;
import com.example.authentication.security.JwtUtil;
import com.example.authentication.security.MyUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticateController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @SneakyThrows
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> createToken(@RequestBody JwtAuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        final JwtAuthenticationResponse jwt = new JwtAuthenticationResponse(jwtUtil.createToken(userDetails));

        return ResponseEntity.ok(jwt);
    }
}
