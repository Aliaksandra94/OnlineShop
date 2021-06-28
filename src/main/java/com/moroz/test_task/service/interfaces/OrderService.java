package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Order;

public interface OrderService {
    Order returnOrderById(long id);
}
