package com.example.demo.application.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ログイン結果クラス
 */
@Data
@AllArgsConstructor
public class LoginResult {
    /**
     * 認証トークン
     */
    private final String token;
}
