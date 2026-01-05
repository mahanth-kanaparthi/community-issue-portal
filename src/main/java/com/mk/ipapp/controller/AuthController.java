package com.mk.ipapp.controller;

import com.mk.ipapp.dto.UserRegisterRequest;
import com.mk.ipapp.dto.UserSummary;
import com.mk.ipapp.dto.auth.AuthRequest;
import com.mk.ipapp.dto.auth.AuthResponse;
import com.mk.ipapp.entity.Region;
import com.mk.ipapp.entity.User;
import com.mk.ipapp.security.JwtTokenProvider;
import com.mk.ipapp.service.RegionService;
import com.mk.ipapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RegionService regionService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, RegionService regionService, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.regionService = regionService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserSummary> register(@RequestBody UserRegisterRequest request){

        UserSummary userSummary = userService.registerUser(request);

        return ResponseEntity.ok(userSummary);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){

        UserSummary user = userService.findByEmail(request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = tokenProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(token);
        response.setExpiresIn(tokenProvider.getExpirationInSeconds());
        response.setUser(user);

        return ResponseEntity.ok(response);
    }


}
