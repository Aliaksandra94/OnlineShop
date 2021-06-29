package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Item;
import com.moroz.test_task.model.Tag;
import com.moroz.test_task.model.User;

import java.util.List;
import java.util.Set;

public interface ItemService {
    List<Item> returnAllItems();
    Item returnItemById(long itemId);
    List<Item> returnItemsByDescription(String description);
    List<Item> returnItemsByTag(Tag tag);
    Item addNewItem(String name, String description, Tag[] tags);
    void editItem(long itemId, String name, String description, Tag[] tags);
    void deleteItem(long itemId);
    boolean isItemAlreadyInBasket(long itemId, User user);
    boolean isItemAlreadyInOrder(long itemId, User user);
}
