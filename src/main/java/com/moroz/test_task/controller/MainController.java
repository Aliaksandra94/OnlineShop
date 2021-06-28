package com.moroz.test_task.controller;


import com.moroz.test_task.service.interfaces.RoleService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@Api(tags = "Controller for start page")
public class MainController {
    RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping

    public String getMainPage(Model model) {
        model.addAttribute("roles", roleService.returnAllRoles());
        return "main/main";
    }
}
