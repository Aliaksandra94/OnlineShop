package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Order;

import java.util.List;

public interface OrderService {
    Order returnOrderById(long id);
    List<Order> returnOrdersByOrderItemId(long itemId);
}
