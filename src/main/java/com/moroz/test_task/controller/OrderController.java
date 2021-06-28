package com.moroz.test_task.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(tags = "Order controller")
@RequestMapping("/main/orders")
public class OrderController {
    @GetMapping
    public String getUserOrders(){
        return "orders/orders";
    }
    @GetMapping("/{id}")
    public String getUserOrder(@PathVariable(name = "id") long id){
        return "orders/order";
    }


}
