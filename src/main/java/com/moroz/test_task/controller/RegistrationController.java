package com.moroz.test_task.controller;

import com.moroz.test_task.model.User;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Controller for registration")
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @GetMapping
    @Operation(
            summary = "User Registration",
            description = "Return page to register new user")
    public HttpStatus getPageToAddNewUser() {
        return HttpStatus.OK;
    }

    @PostMapping(produces = "application/json")
    @Operation(
            summary = "User Registration",
            description = "Allows to register new user")
    public User addNewUser(
            @RequestParam(value = "login") String login,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        User user = userService.addNewUser(login, password, email);
        return user;
    }
}
