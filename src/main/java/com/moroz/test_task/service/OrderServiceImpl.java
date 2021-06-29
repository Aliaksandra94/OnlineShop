package com.moroz.test_task.service;

import com.moroz.test_task.model.Order;
import com.moroz.test_task.repository.OrderDAO;
import com.moroz.test_task.repository.OrderItemDAO;
import com.moroz.test_task.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public Order returnOrderById(long id) {
        return orderDAO.getById(id);
    }

    @Override
    public List<Order> returnOrdersByOrderItemId(long itemId) {
        return orderDAO.findByOrderItems(orderItemDAO.findByItemId(itemId));
    }
}
