package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Basket;
import com.moroz.test_task.model.BasketItem;
import com.moroz.test_task.model.User;

import java.util.List;

public interface BasketService {
    void addItemToTheBasket(User user, long itemId);
    List<BasketItem> returnBasketItems(User user);
    long placedOrder(Basket basket);
    List<BasketItem> deleteItem(User user, long id);
    void cleanBasket(User user);
}
