package com.polyrepo.analyzer.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TokenStore {
    private final Map< String, Authentication > cache = new HashMap<>();

    public String generateToken( Authentication authentication ) {
        String token = UUID.randomUUID().toString();
        cache.put( token, authentication );
        System.out.println("\n\n TOKEN : "+token);
        System.out.println("Authentication : "+authentication);
        return token;
    }

    public Authentication getAuth( String token ) {
        return cache.getOrDefault( token, null );
    }
}
