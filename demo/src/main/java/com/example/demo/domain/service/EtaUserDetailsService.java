package com.example.demo.domain.service;

import java.util.Optional;

import com.example.demo.AppConfig;
import com.example.demo.domain.model.UserDetailsAdapter;
import com.example.demo.domain.model.UserModel;
import com.example.demo.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EtaUserDetailsService implements UserDetailsService {
    private final AppConfig appConfig;
    private final UserRepository userRepository;

    @Autowired
    public EtaUserDetailsService(UserRepository userRepository, AppConfig appConfig) {
        this.userRepository = userRepository;
        this.appConfig = appConfig;
    }

    public Optional<UserModel> find(String loginId) {
        return userRepository.findOneByLoginId(loginId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findOneByLoginId(username)
            .map(user -> new UserDetailsAdapter(user, this.appConfig))
            .orElseThrow(() -> new UsernameNotFoundException("User is not found."));
    }
}
