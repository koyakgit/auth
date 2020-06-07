package com.example.demo.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * 認証ユーティリティクラス
 */
public final class AuthorityUtils {
    public static Set<GrantedAuthority> getAuthorities(String role) {
        final String[] roles = { role };
        return Arrays.asList(roles).stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toSet());
    }
}
