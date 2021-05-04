package com.example.demo.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(new HashSet<Permision>(Arrays.asList(Permision.READING))),
    ADMIN(new HashSet<Permision>(Arrays.asList(Permision.READING, Permision.WRITING)));

    Role(Set<Permision> permissions) {
        this.permissions = permissions;
    }

    private final Set<Permision> permissions;

    /*
    transform our permissions into granted authorities, known by Spring security
     */
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
    }
}
