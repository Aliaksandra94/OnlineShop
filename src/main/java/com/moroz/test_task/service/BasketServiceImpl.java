package com.moroz.test_task.service;

import com.moroz.test_task.model.*;
import com.moroz.test_task.repository.*;
import com.moroz.test_task.service.interfaces.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {
    private BasketItemDAO basketItemDAO;
    private ItemDAO itemDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;


    @Autowired
    public void setBasketItemDAO(BasketItemDAO basketItemDAO) {
        this.basketItemDAO = basketItemDAO;
    }

    @Autowired
    public void setItemDAO(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public List<BasketItem> returnBasketItems(User user) {
        return basketItemDAO.findByBasketId(user.getBasket().getId());
    }

    @Override
    public void addItemToTheBasket(User user, long itemId) {
        if (user.getBasket() == null) {
            user.setBasket(new Basket(user, new ArrayList<>()));
        }
        Item item = itemDAO.getById(itemId);
        BasketItem basketItem = new BasketItem(user.getBasket());
        item.getBasketItems().add(basketItem);
        basketItemDAO.save(basketItem);
        itemDAO.save(item);
    }


    @Override
    public List<BasketItem> deleteItem(User user, long id) {
        BasketItem basketItem = user.getBasket().getBasketItems().stream().filter(s -> s.getItems().get(0).getId() == id).collect(Collectors.toList()).get(0);
        user.getBasket().getBasketItems().remove(basketItem);
        itemDAO.getById(id).getBasketItems().remove(basketItem);
        basketItemDAO.delete(basketItem);
        return basketItemDAO.findByBasketId(user.getBasket().getId());
    }

    @Override
    public void cleanBasket(User user) {
        while (!user.getBasket().getBasketItems().isEmpty()) {
            deleteItem(user, user.getBasket().getBasketItems().get(0).getItems().get(0).getId());
        }
    }

    @Override
    public long placedOrder(Basket basket) {
        Order order = new Order();
        order.setUser(basket.getUser());
        orderDAO.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        for (BasketItem basketItem : basket.getBasketItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            for (Item item : basketItem.getItems()) {
                orderItem.setItem(item);
                orderItemDAO.save(orderItem);
                orderItems.add(orderItem);
            }
        }
        order.setOrderItems(orderItems);
        cleanBasket(basket.getUser());
        return order.getId();
    }
}
