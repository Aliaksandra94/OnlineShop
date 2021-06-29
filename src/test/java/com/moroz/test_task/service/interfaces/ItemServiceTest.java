package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Item;
import com.moroz.test_task.model.Tag;
import com.moroz.test_task.model.User;
import com.moroz.test_task.repository.ItemDAO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemDAO itemDAO;

    @Test
    void returnAllItems() {
        Mockito.when(itemDAO.findAll()).thenReturn(Stream.of(new Item("Milk", "DAIRY", Tag.values()), new Item("Cheese", "DAIRY", Tag.values())).collect(Collectors.toList()));
        Assert.assertEquals(2, itemService.returnAllItems().size());
    }

    @Test
    void returnAllItems_NOT_NULL() {
        Assert.assertNotNull(itemService.returnAllItems());
    }

    @Test
    void returnItemById() {
        long id = 1;
        Item item = new Item("Milk", "dairy", Tag.values());
        Mockito.doReturn(new Item("Milk", "daity", Tag.values())).when(itemDAO).getById(id);
        Assert.assertEquals(item, itemService.returnItemById(id));
    }

    @Test
    void returnItemsByDescription() {
        String description = "Dairy";
        String description2 = "Milk";
        String descriptionRegex = "(\\w*" + description.toLowerCase() + "\\w*)";
        String descriptionRegex2 = "(\\w*" + description2.toLowerCase() + "\\w*)";
        Mockito.doReturn(Stream.of(
                new Item("Milk", "dairy", Tag.values()),
                new Item("Cheese", "cheese", Tag.values()),
                new Item("Cheese", "dairy", Tag.values())).
                filter(s -> s.getDescription().matches(descriptionRegex))
                .collect(Collectors.toList())).when(itemDAO).findByDescription(description);
        Mockito.doReturn(Stream.of(
                new Item("Milk", "dairy", Tag.values()),
                new Item("Cheese", "cheese", Tag.values()),
                new Item("Cheese", "dairy", Tag.values())).
                filter(s -> s.getDescription().matches(descriptionRegex2))
                .collect(Collectors.toList())).when(itemDAO).findByDescription(description2);
        Assert.assertEquals(0, itemService.returnItemsByDescription(description2).size());
    }

    @Test
    void returnItemsByTag() {
        Tag tag = Tag.DISCOUNT;
        Mockito.doReturn(Stream.of(
                new Item("Milk", "dairy", new Tag[]{Tag.BEST_OFFER, Tag.LAST_PRODUCT}),
                new Item("Cheese", "cheese", Tag.values()),
                new Item("Cheese", "dairy", Tag.values())).
                filter(s -> s.getTags().contains(tag))
                .collect(Collectors.toList())).when(itemDAO).findByTags(tag);
        Assert.assertEquals(2, itemService.returnItemsByTag(tag).size());
    }

    @Test
    void addNewItem() {
        Item itemActual = new Item("Milk", "dairy", new Tag[]{Tag.BEST_OFFER, Tag.LAST_PRODUCT});
        Item itemExpected = itemService.addNewItem("Milk", "dairy", new Tag[]{Tag.BEST_OFFER, Tag.LAST_PRODUCT});
        Assert.assertNotNull(itemExpected);
        Assert.assertEquals(itemActual.getName(), itemExpected.getName());
        Assert.assertEquals(itemActual.getDescription(), itemExpected.getDescription());
        Assert.assertEquals(itemActual.getTags(), itemExpected.getTags());
        Assert.assertEquals(itemActual, itemExpected);
        Assert.assertNotNull(itemExpected.getBasketItems());
        Assert.assertNotNull(itemExpected.getOrderItems());
        Mockito.verify(itemDAO, Mockito.times(1)).save(itemExpected);
    }

    @Test
    void editItem() {
        long id = 1;
        Tag[] tags = {Tag.BEST_OFFER, Tag.LAST_PRODUCT};
        Item itemActual = new Item("Milk", "dairy", tags);
        itemActual.setName("Cheese");
        itemActual.setDescription("meat");
        Mockito.doReturn(new Item(
                "Milk", "dairy", new Tag[]{Tag.BEST_OFFER, Tag.LAST_PRODUCT}))
                .when(itemDAO).getById(id);
        Assert.assertEquals(itemActual, itemService.editItem(id, "Cheese", "meat", tags));
    }

}