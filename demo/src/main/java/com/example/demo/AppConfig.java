package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AppConfig {
    /**
     * 許容するログインリトライ回数
     */
    @Value("#{new Integer('${security.limit-login-retry-count}')}")
    private Integer limitLoginRetryCount = Integer.MAX_VALUE;
}
