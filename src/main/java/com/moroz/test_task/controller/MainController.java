package com.moroz.test_task.controller;


import com.moroz.test_task.model.User;
import com.moroz.test_task.service.interfaces.RoleService;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/main")
@Api(tags = "Controller for start page")
public class MainController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation("Get basket's page")
    public User getMainPage(HttpServletRequest request) {
        return userService.returnUserByLogin(request.getUserPrincipal().getName());
    }

}
