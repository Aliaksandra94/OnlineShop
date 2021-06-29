package com.moroz.test_task.service;

import com.moroz.test_task.model.*;
import com.moroz.test_task.repository.BasketItemDAO;
import com.moroz.test_task.repository.ItemDAO;
import com.moroz.test_task.repository.OrderItemDAO;
import com.moroz.test_task.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private ItemDAO itemDAO;
    private BasketItemDAO basketItemDAO;
    private OrderItemDAO orderItemDAO;

    @Autowired
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Autowired
    public void setBasketItemDAO(BasketItemDAO basketItemDAO) {
        this.basketItemDAO = basketItemDAO;
    }

    @Autowired
    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public List<Item> returnAllItems() {
        return itemDAO.findAll();
    }

    @Override
    public Item returnItemById(long itemId) {
        return itemDAO.getById(itemId);
    }

    @Override
    public List<Item> returnItemsByDescription(String description) {
        return itemDAO.findByDescription(description);
    }

    @Override
    public List<Item> returnItemsByTag(Tag tag) {
        return itemDAO.findByTags(tag);
    }

    @Override
    public Item addNewItem(String name, String description, Tag[] tags) {
        Item item = new Item(name, description, tags);
        itemDAO.save(item);
        return item;
    }

    @Override
    public void editItem(long itemId, String name, String description, Tag[] tags) {
        Item item = itemDAO.getById(itemId);
        if (!(name.equals(null) || name.equals(""))) {
            item.setName(name);
        }
        if (!(description == (null) || description.equals(""))) {
            item.setDescription(description);
        } else {
            item.setDescription(item.getDescription());
        }
        if (tags.length == 0) {
            for (Tag tag : tags) {
                item.setTags(item.getTags());
            }
        } else {
            Set<Tag> itemTags = EnumSet.noneOf(Tag.class);
            for (Tag tag : tags) {
                itemTags.add(tag);
            }
            item.setTags(itemTags);
        }
        itemDAO.save(item);
    }

    @Override
    public void deleteItem(long itemId) {
        Item item = itemDAO.getById(itemId);
        itemDAO.delete(item);
    }

    @Override
    public boolean isItemAlreadyInBasket(long itemId, User user) {
        if (user.getBasket().getBasketItems().stream().anyMatch(basketItem -> basketItem.getItems().get(0).getId() == itemId)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isItemAlreadyInOrder(long itemId, User user) {
        for (Order order : user.getOrders()) {
            if (order.getOrderItems().contains(orderItemDAO.findByItemId(itemId))){
                return true;
            }
        }
        return false;
    }
}
