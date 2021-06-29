package com.moroz.test_task.controller;

import com.moroz.test_task.model.User;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Api(tags = "Controller for login and authorization")
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @Operation(
            summary = "User authorization",
            description = "Allows the user to log in")
    public HttpStatus getAuthenticationAndAuthorization() {
        return HttpStatus.OK;
    }

//For present in SWAGGER
    @PostMapping(produces = "application/json")
    @Operation(
            summary = "User Login",
            description = "Check user login and password")
    public User getLogin(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "password") String password) {
        return userService.checkUserForLogIn(login, password);
    }

    @GetMapping("/successEnter")
    public User successEnter(Principal principal) {
        return userService.returnUserByLogin(principal.getName());
    }

    @GetMapping("/failedEnter")
    public HttpStatus failedEnter(Model model) {
        return HttpStatus.PERMANENT_REDIRECT;
    }
    @PostMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Logout")
    public HttpStatus getLogout() {
        return HttpStatus.OK;
    }

}
