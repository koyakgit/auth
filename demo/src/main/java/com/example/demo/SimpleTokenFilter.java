package com.example.demo;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.domain.model.UserDetailsAdapter;
import com.example.demo.domain.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleTokenFilter extends GenericFilterBean {

    private final UserRepository userRepository;
    private final AppConfig appConfig;
    private final Algorithm algorithm;

    public SimpleTokenFilter(UserRepository userRepository, String secretKey, AppConfig appConfig) {
        Objects.requireNonNull(secretKey, "secret key must be not null");
        this.userRepository = userRepository;
        this.appConfig = appConfig;
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authentication(verifyToken(token));
        } catch (JWTVerificationException e) {
            log.error("verify token error", e);
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }

    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private void authentication(DecodedJWT jwt) {
        final String loginId = jwt.getSubject();
        userRepository.findOneByLoginId(loginId).ifPresent(user -> {
            UserDetailsAdapter simpleLoginUser = new UserDetailsAdapter(user, this.appConfig);
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    simpleLoginUser, null, simpleLoginUser.getAuthorities()));
        });
    }

}
