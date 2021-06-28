package com.moroz.test_task.service;

import com.moroz.test_task.model.Item;
import com.moroz.test_task.model.Order;
import com.moroz.test_task.model.OrderItem;
import com.moroz.test_task.model.Tag;
import com.moroz.test_task.repository.ItemDAO;
import com.moroz.test_task.repository.OrderDAO;
import com.moroz.test_task.repository.OrderItemDAO;
import com.moroz.test_task.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private ItemDAO itemDAO;
    private OrderItemDAO orderItemDAO;

    @Autowired
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
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
    public void addNewItem(String name, String description, Tag[] tags) {
        Item item = new Item(name, description, tags);
        itemDAO.save(item);
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
    public boolean isItemAlreadyInBasketOrInOrder(long itemId) {
        Item item = itemDAO.getById(itemId);
        if (item.getBasketItems().contains(item)) {
            return true;
        }
        List<OrderItem> orderItems = orderItemDAO.findByItemId(itemId);
        if (orderItems.size() != 0 || !orderItems.equals(null)){
            return true;
        }
            return false;
    }
}
