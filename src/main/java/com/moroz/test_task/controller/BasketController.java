package com.moroz.test_task.controller;

import com.moroz.test_task.model.BasketItem;
import com.moroz.test_task.model.Order;
import com.moroz.test_task.model.OrderItem;
import com.moroz.test_task.model.User;
import com.moroz.test_task.service.interfaces.BasketService;
import com.moroz.test_task.service.interfaces.OrderService;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "Controller for basket")
@RequestMapping(name = "/main/basket")
public class BasketController {
    private UserService userService;
    private BasketService basketService;
    private OrderService orderService;
    private JavaMailSender mailSender;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation(value = "Get basket's page",
            authorizations = {@Authorization(value = "USER")})
    @Authorization(value = "USER")
    public List<BasketItem> getBasketPage(HttpServletRequest request) {
        return basketService.returnBasketItems(userService.returnUserByLogin(request.getUserPrincipal().getName()));
    }


    @DeleteMapping(value = "/{itemId}/delete", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Delete item from the basket")
    @Authorization(value = "USER")
    public List<BasketItem> deleteItemFromTheBasket(HttpServletRequest request, @PathVariable("itemId") long itemId) {
        User user = userService.returnUserByLogin(request.getUserPrincipal().getName());
        return basketService.deleteItem(user, itemId);
    }


    @PostMapping(value = "/placedOrder", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Placed order")
    @Authorization(value = "USER")
    public Order placedOrder(HttpServletRequest request) throws MessagingException {
        User user = userService.returnUserByLogin(request.getUserPrincipal().getName());
        long orderId = basketService.placedOrder(user.getBasket());
        Order order = orderService.returnOrderById(orderId);
        MimeMessage message = mailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        String htmlMsg = "<h2> Good day, " + user.getLogin() + "! </h2>\n" +
                "<h3>Thank you for placing the order. <br> " +
                "Your order No " + order.getId() + " dd " + LocalDate.now() + ". " +
                "<br><br>" +
                "Order summary <br>" +
                order.getOrderItems().size() + " items: <br>";
        for (OrderItem orderItem : order.getOrderItems()) {
            String item = orderItem.getItem().getName();
            htmlMsg += item + ";" + "<br>";
        }
        htmlMsg += "<br><br>" +
                "To get more info, please, visit our web-site: <a href=\"http://localhost:8080\">Link</a>" +
                "<br><br>" +
                "Thank you for choosing us!</h3>";
        message.setContent(htmlMsg, "text/html");
        helper.setTo(user.getEmail());
        helper.setSubject("Order confirmation.");
        this.mailSender.send(message);
        return order;
    }
}