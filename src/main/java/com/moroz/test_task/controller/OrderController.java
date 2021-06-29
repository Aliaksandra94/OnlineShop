package com.moroz.test_task.controller;

import com.moroz.test_task.model.Order;
import com.moroz.test_task.service.interfaces.OrderService;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Api(tags = "Order controller")
@RequestMapping("/main/orders")
public class OrderController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Get order's page")
    @Authorization(value = "USER")
    public List<Order> getUserOrders(HttpServletRequest request) {
        return userService.returnUserByLogin(request.getUserPrincipal().getName()).getOrders();
    }

    @GetMapping(value = "/{id}/order", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Get order page")
    @Authorization(value = "USER")
    public Order getUserOrder(@PathVariable(name = "id") long id) throws NullPointerException{
        Order order = orderService.returnOrderById(id);
        return order;
    }


}
