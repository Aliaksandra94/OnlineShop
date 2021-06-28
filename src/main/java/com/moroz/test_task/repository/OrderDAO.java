package com.moroz.test_task.repository;

import com.moroz.test_task.model.Order;
import com.moroz.test_task.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    List<Order> findByOrderItems(OrderItem orderItem);
}