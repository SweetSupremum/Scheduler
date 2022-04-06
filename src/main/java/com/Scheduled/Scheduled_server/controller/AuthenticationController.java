package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.dto.CustomerAuthDto;
import com.Scheduled.Scheduled_server.dto.TokenDto;
import com.Scheduled.Scheduled_server.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthService authService;
    @PostMapping("/login")
    @Operation(summary = "login", description = "user login with JWT token")
    public ResponseEntity<?> authorization(@RequestBody CustomerAuthDto customerAuthDto) {
        System.out.println(customerAuthDto);
        return new ResponseEntity<>(new TokenDto(authService.authorization(customerAuthDto)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }
}
