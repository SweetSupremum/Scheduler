package com.Scheduled.Scheduled_server.security;

import com.Scheduled.Scheduled_server.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return UserDetailsImpl
                .build(customerRepository.findByUserName(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists")));
    }
}
