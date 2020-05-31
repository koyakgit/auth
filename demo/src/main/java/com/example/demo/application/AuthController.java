package com.example.demo.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// @RestController
@Controller
@RequestMapping("/")
public class AuthController {

    public String test() {
        return "Hello, world.";
    }
    /**
     * ログインAPI
     * @param request リクエストパラメータ
     * @return ログイン結果
     */
    // @RequestMapping(path = "login", method = RequestMethod.GET, 
    //     produces = MediaType.APPLICATION_JSON_VALUE +  ";charset=utf-8")
    @RequestMapping(path="login", method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }
    // public LoginResult login(@RequestBody LoginRequest request) {
    //     return new LoginResult("dummy token.");
    // }
}
