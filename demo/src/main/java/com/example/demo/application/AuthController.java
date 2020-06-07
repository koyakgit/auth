package com.example.demo.application;

import java.util.Map;

import com.example.demo.domain.model.UserDetailsAdapter;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class AuthController {

    @GetMapping
    public String greeting(@AuthenticationPrincipal UserDetailsAdapter user) {
        return "hello " + user.getUsername();
    }

    @GetMapping(path = "echo/{message}")
    public String getEcho(@PathVariable(name = "message") String message) {
        return message.toUpperCase();
    }

    @PostMapping(path = "echo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String postEcho(@RequestBody Map<String, String> message) {
        return message.toString();
    }

}
