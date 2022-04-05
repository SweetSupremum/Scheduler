package com.Scheduled.Scheduled_server.config;

import com.Scheduled.Scheduled_server.model.Role;
import com.Scheduled.Scheduled_server.security.jwt.JwtConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConfigurer jwtConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /*.cors().and()
                .csrf()*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/library**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.GET, "/games**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/games**").hasAuthority(Role.ADMIN.getAuthority())
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/logout").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
