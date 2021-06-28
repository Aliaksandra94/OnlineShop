package com.moroz.test_task.controller;

import com.moroz.test_task.model.BasketItem;
import com.moroz.test_task.model.Item;
import com.moroz.test_task.model.Tag;
import com.moroz.test_task.model.User;
import com.moroz.test_task.service.interfaces.BasketService;
import com.moroz.test_task.service.interfaces.ItemService;
import com.moroz.test_task.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "Controller for catalog's page")
@RequestMapping("/main/catalog")
public class CatalogController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private BasketService basketService;

    @GetMapping
    public List<Item> getCatalog() {
//        if (itemService.returnAllItems() == null){
//            return new ArrayList<>();
//        } else {
        List<Item> items = itemService.returnAllItems();
        Tag[] tags = Tag.values();
        return items;
    }
    // }

    @PostMapping("/byDescription")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN', 'USER')")
    @ApiOperation("Get list of items by description")
    @Authorization(value = "ADMIN, USER")
    public List<Item> getCatalogByDescription(@RequestParam("description") String description) {
        return itemService.returnItemsByDescription(description);
    }

    @PostMapping("/byTag")
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

    @PostMapping("/new")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Add new item")
    @Authorization(value = "ADMIN")
    public void addNewItem(@RequestParam("name") String name, @RequestParam("description") String description,
                           @RequestParam("tags") Tag[] tags) {
        itemService.addNewItem(name, description, tags);
    }

    @GetMapping("/{itemId}/edit")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Edit item")
    @Authorization(value = "ADMIN")
    public HttpStatus getPageToEditItem(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemService.returnItemById(itemId);
        model.addAttribute("item", item);
        return HttpStatus.OK;
    }

    @PostMapping("/{itemId}/edit")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Edit item")
    @Authorization(value = "ADMIN")
    public HttpStatus editItem(@RequestParam("itemId") long itemId, @RequestParam(required = false, value = "name", defaultValue = "") String name,
                               @RequestParam(required = false, value = "description", defaultValue = "") String description,
                               @RequestParam(required = false, value = "tags", defaultValue = "") Tag[] tags) {
        if (!itemService.isItemAlreadyInBasketOrInOrder(itemId)) {
            itemService.editItem(itemId, name, description, tags);
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @DeleteMapping("/{itemId}/delete")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @ApiOperation("Delete item")
    @Authorization(value = "ADMIN")

    public HttpStatus editItem(@RequestParam("itemId") long itemId) {
        if (!itemService.isItemAlreadyInBasketOrInOrder(itemId)) {
            itemService.deleteItem(itemId);
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }


    @GetMapping("/addToBasket")
    @PreAuthorize(value = "hasAuthority('USER')")
    @ApiOperation("Add item to the basket")
    @Authorization(value = "USER")
    public void addProductToBasket(HttpServletRequest request, @RequestParam("itemId") long itemId) {
        User user = userService.returnUserByLogin(request.getUserPrincipal().getName());
        basketService.addItemToTheBasket(user, itemId);
    }

}


//    public ResponseEntity<APIResponse<Data>> create(@Valid @RequestBody OrganisationCreateRequest createRequest, BindingResult bindingResult, HttpServletRequest request) {
//
//        RequestContext requestContext = wiseconnectSecurityContextProvider.getRequestContext();
//        LOGGER.debug("Create Organisation request received");
//        if (bindingResult.hasErrors()) {
//            LOGGER.error(INPUT_VALIDATION_ERROR);
//            throw new ControllerException(bindingResult, INPUT_VALIDATION_ERROR);
//        }
//
//        OrganisationView organisationView = organisationService.createOrganisation(createRequest, requestContext);
//
//        LOGGER.debug("Created Organisation {} successfully", organisationView.getId());
//        APIResponse<Data> apiResponse = new APIResponse<>(CREATE_SUCCESS, "Organisation Created", new Data(organisationView.getId()));
//        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//    }