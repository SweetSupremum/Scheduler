package com.Scheduled.Scheduled_server.security.jwt;

import com.Scheduled.Scheduled_server.error.advice.custom.JwtAuthenticationException;
import com.Scheduled.Scheduled_server.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Date;

import static java.time.temporal.ChronoField.MILLI_OF_SECOND;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;

    private final Environment environment;

    private String secretKey;
    private String authorizationHeader;
    private long expired;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(environment.getProperty("secret").getBytes());
        authorizationHeader = environment.getProperty("header");
        expired = Long.parseLong(environment.getProperty("expiration"));
    }

    public String createToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expired * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(validity)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}
