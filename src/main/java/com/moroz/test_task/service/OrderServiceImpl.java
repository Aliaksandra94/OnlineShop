package com.moroz.test_task.service;

import com.moroz.test_task.model.Order;
import com.moroz.test_task.repository.OrderDAO;
import com.moroz.test_task.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public Order returnOrderById(long id) {
        return orderDAO.getById(id);
    }
}
