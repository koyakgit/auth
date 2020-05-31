package com.example.demo.application.request;

import lombok.Data;

/**
 * ログインリクエストクラス
 */
@Data
public class LoginRequest {
    /**
     * ログインID
     */
    private String loginId;

    /**
     * パスワード
     */
    private String password;
}
