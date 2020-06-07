package com.example.demo.domain.model;

import java.util.List;

import com.example.demo.AppConfig;
// import com.example.demo.utils.AuthorityUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class UserDetailsAdapter extends User {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 1L;

    @Getter
    private final UserModel userModel;

    private static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    public UserDetailsAdapter(UserModel userModel, AppConfig appConfig) {
        super(
            userModel.getLoginId(), 
            userModel.getPassword(), 
            userModel.getIsEnable(), 
            !isExpired(userModel, appConfig), 
            !isCredencialExpired(userModel, appConfig),
            !isLocked(userModel, appConfig), 
            USER_ROLES);
        this.userModel = userModel;
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
