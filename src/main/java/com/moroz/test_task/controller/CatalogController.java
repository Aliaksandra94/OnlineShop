package com.moroz.test_task.controller;

import com.moroz.test_task.model.*;
import com.moroz.test_task.service.interfaces.BasketService;
import com.moroz.test_task.service.interfaces.ItemService;
import com.moroz.test_task.service.interfaces.OrderService;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "Controller for catalog's page")
@RequestMapping("/main/catalog")
public class CatalogController {
    private ItemService itemService;
    private UserService userService;
    private BasketService basketService;
    private MessageSource messageSource;
    private OrderService orderService;
    private JavaMailSender mailSender;


    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
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
    public List<Item> getCatalog() {
        if (itemService.returnAllItems() == null) {
            return new ArrayList<>();
        } else {
            List<Item> items = itemService.returnAllItems();
            return items;
        }
    }

    @PostMapping(value = "/byDescription", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    @ApiOperation("Get list of items by description")
    @Authorization(value = "ADMIN, USER")
    public List<Item> getCatalogByDescription(@RequestParam("description") String description) {
        return itemService.returnItemsByDescription(description);
    }

    @PostMapping(value = "/byTag", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    @ApiOperation("Get list of items by description")
    @Authorization(value = "ADMIN, USER")
    public List<Item> getCatalogByTag(HttpServletRequest request, @RequestParam("tag") Tag tag) {
        return itemService.returnItemsByTag(tag);
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Get page to create new item")
    @Authorization(value = "ADMIN")
    public HttpStatus getPageToAddNewItem() {
        return HttpStatus.OK;
    }

    @PostMapping(value = "/new", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Add new item")
    @Authorization(value = "ADMIN")
    public Item addNewItem(@RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("tags") Tag[] tags) {
        return itemService.addNewItem(name, description, tags);
    }

    @GetMapping(value = "/{itemId}/edit", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Page for edit item")
    @Authorization(value = "ADMIN")
    public HttpStatus getPageToEditItem(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemService.returnItemById(itemId);
        model.addAttribute("item", item);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/{itemId}/edit", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Edit item")
    @Authorization(value = "ADMIN")
    public Item editItem(HttpServletRequest request, @RequestParam("itemId") long itemId, @RequestParam(required = false, value = "name", defaultValue = "") String name,
                           @RequestParam(required = false, value = "description", defaultValue = "") String description,
                           @RequestParam(required = false, value = "tags", defaultValue = "") Tag[] tags) {
        List<User> users = userService.returnUsersByRoleId(2);
        String message = "";
        for (User user : users) {
            if (itemService.isItemAlreadyInBasket(itemId, user) || itemService.isItemAlreadyInOrder(itemId, user)) {
                message = HttpStatus.FORBIDDEN + ": " +
                        messageSource.getMessage("error.edit.item.already.in.basket.or.item.ordered",
                                new Object[]{"error.edit.item.already.in.basket.or.item.ordered"}, LocaleContextHolder.getLocale());
            } else {
                itemService.editItem(itemId, name, description, tags);
                message = HttpStatus.OK + ": " + messageSource.getMessage("success.item.edit",
                        new Object[]{"success.item.edit"}, LocaleContextHolder.getLocale());
            }
        }
        return itemService.returnItemById(itemId);
    }

    @PostMapping(value = "/{itemId}/forceEdit", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Force edit item")
    @Authorization(value = "ADMIN")
    public Item forceEditItem(@RequestParam("itemId") long itemId, @RequestParam(required = false, value = "name", defaultValue = "") String name,
                              @RequestParam(required = false, value = "description", defaultValue = "") String description,
                              @RequestParam(required = false, value = "tags", defaultValue = "") Tag[] tags) throws MessagingException {
        List<User> users = userService.returnUsersByRoleId(2);
        for (User user : users) {
            if (itemService.isItemAlreadyInBasket(itemId, user)) {
                MimeMessage message = mailSender.createMimeMessage();
                boolean multipart = true;
                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                String htmlMsg = "<h2> Good day, " + user.getLogin() + "! </h2>\n" +
                        "Your received this message because you add to the Basket  the " +
                        "item " + itemService.returnItemById(itemId).getName() + ". " +
                        "This item still available in your basket with new data: ";
                itemService.editItem(itemId, name, description, tags);
                htmlMsg += "Item: " + itemService.returnItemById(itemId).getName() + ", " +
                        "Description: " + itemService.returnItemById(itemId).getDescription() + ", " +
                        "Tags: " + itemService.returnItemById(itemId).getTags() + "." +
                        "<br><br>" +
                        "Thank you for choosing us!</h3>";
                message.setContent(htmlMsg, "text/html");
                helper.setTo(user.getEmail());
                helper.setSubject("About new item's data.");
                this.mailSender.send(message);
            }
        }
        return itemService.returnItemById(itemId);
    }

    @DeleteMapping(value = "/{itemId}/delete", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Delete item")
    @Authorization(value = "ADMIN")
    public String deleteItem(HttpServletRequest request, @RequestParam("itemId") long itemId) {
        List<User> users = userService.returnUsersByRoleId(2);
        String message = "";
        for (User user : users) {
            if (itemService.isItemAlreadyInBasket(itemId, user) || itemService.isItemAlreadyInOrder(itemId, user)) {
                message = HttpStatus.FORBIDDEN + ": " +
                        messageSource.getMessage("error.item.already.in.basket.or.item.ordered",
                                new Object[]{"error.item.already.in.basket.or.item.ordered"}, LocaleContextHolder.getLocale());
            } else {
                basketService.deleteItem(user, itemId);
                message = HttpStatus.OK +
                        ": " + messageSource.getMessage("success.item.deleted",
                        new Object[]{"success.item.deleted"}, LocaleContextHolder.getLocale());
            }
        }
        return message;
    }


    @GetMapping(value = "/addToBasket", produces = "application/json")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Add item to the basket")
    @Authorization(value = "USER")
    public List<BasketItem> addProductToBasket(HttpServletRequest request, @RequestParam("itemId") long itemId) {
        User user = userService.returnUserByLogin(request.getUserPrincipal().getName());
        String message = "";
        if (itemService.isItemAlreadyInBasket(itemId, user)) {
            message = HttpStatus.FORBIDDEN + ": " +
                    messageSource.getMessage("error.item.already.in.basket",
                            new Object[]{"error.item.already.in.basket"}, LocaleContextHolder.getLocale());
        } else {
            basketService.addItemToTheBasket(user, itemId);
            message =
                    HttpStatus.OK +
                            ": " + messageSource.getMessage("success.item.add.in.basket",
                            new Object[]{"success.item.add.in.basket"}, LocaleContextHolder.getLocale());
        }
        return user.getBasket().getBasketItems();
    }
}
