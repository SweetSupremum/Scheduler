package com.Scheduled.Scheduled_server.security;

import com.Scheduled.Scheduled_server.model.Customer;
import com.Scheduled.Scheduled_server.model.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Customer customer;
    private boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails build(Customer customer) {
        List<SimpleGrantedAuthority> authorities = List
                .of(new SimpleGrantedAuthority(customer.getRole().getAuthority()));
        return new User(
                customer.getUserName(),
                customer.getPassword(),
                true,
                true,
                true,
                customer.getStatus().equals(Status.ACTIVE),
                authorities
        );
    }
}
