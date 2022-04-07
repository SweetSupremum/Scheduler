package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.dto.CustomerAuthDto;
import com.Scheduled.Scheduled_server.error.advice.custom.UserBannedException;
import com.Scheduled.Scheduled_server.model.Customer;
import com.Scheduled.Scheduled_server.model.Status;
import com.Scheduled.Scheduled_server.repository.CustomerRepository;
import com.Scheduled.Scheduled_server.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String authorization(CustomerAuthDto customerAuthDto) {
        String userName = customerAuthDto.getUserName();
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName, customerAuthDto.getPassword()));
        Customer customer = Optional.of(customerRepository
                .findByUserName(userName)
                .filter(item -> item.getStatus().name().equals(Status.ACTIVE.name()))
                .orElseThrow(UserBannedException::new))
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        log.info("User -> {}", customerAuthDto);
        return jwtTokenProvider.createToken(userName, customer.getRole().name());

    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
    }

}
