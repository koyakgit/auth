package com.example.demo.domain.model;

import com.example.demo.AppConfig;
import com.example.demo.utils.AuthorityUtils;

import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class UserDetailsAdapter extends User {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 1L;

    @Getter
    private final UserModel userModel;

    public UserDetailsAdapter(UserModel user, AppConfig appConfig) {
        super(
            user.getLoginId(), 
            user.getPassword(), 
            user.getIsEnable(), 
            !isExpired(user, appConfig), 
            !isCredencialExpired(user, appConfig),
            !isLocked(user, appConfig), 
            AuthorityUtils.getAuthorities(user.getRole()));
        this.userModel = user;
    }

    private static boolean isExpired(UserModel user, AppConfig appConfig) {
        return false;
    }

    private static boolean isCredencialExpired(UserModel user, AppConfig appConfig) {
        return false;
    }

    private static boolean isLocked(UserModel user, AppConfig appConfig) {
        return user.getLoginRetryCount().intValue() > appConfig.getLimitLoginRetryCount().intValue();
    }
}
