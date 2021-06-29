package com.moroz.test_task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessDeniedController {
    @GetMapping(value = "/403", produces = "application/json")
    public HttpStatus accessDenied() {
        return HttpStatus.FORBIDDEN;
    }
}
