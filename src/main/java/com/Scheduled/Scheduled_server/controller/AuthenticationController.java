package com.Scheduled.Scheduled_server.controller;

import com.Scheduled.Scheduled_server.dto.CustomerAuthDto;
import com.Scheduled.Scheduled_server.dto.TokenDto;
import com.Scheduled.Scheduled_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "/auth/example.js")
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authorization(@RequestBody CustomerAuthDto customerAuthDto) {

        return new ResponseEntity<>(new TokenDto(authService.authorization(customerAuthDto)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
    }

    @GetMapping("/login")
    public String auth() {
        return "auth";
    }
}
