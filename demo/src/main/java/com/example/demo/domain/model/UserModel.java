package com.example.demo.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class UserModel {
    @Id
    private String _id;

    private String loginId;

    private String password;

    private String name;

    private String role;

    private Boolean isEnable;

    private Integer loginRetryCount;

    private Date lastUpdateDate;
    
}
