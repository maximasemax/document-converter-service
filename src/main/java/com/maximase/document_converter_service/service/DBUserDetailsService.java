package com.maximase.document_converter_service.service;

import com.maximase.document_converter_service.entity.User;
import com.maximase.document_converter_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBUserDetailsService implements UserDetailsService {

    private final UserRepository users;          // только один репозиторий


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User u = users.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User '%s' not found".formatted(username)));

        // конвертируем Set<Role> → List<GrantedAuthority>
        List<SimpleGrantedAuthority> authorities = u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                u.isEnabled(),        // enabled
                true,                 // accountNonExpired
                true,                 // credentialsNonExpired
                true,                 // accountNonLocked
                authorities);
    }
}
