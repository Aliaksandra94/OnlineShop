package com.moroz.test_task.repository;

import com.moroz.test_task.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Long> {
    @Query(value = "from OrderItem oi where oi.item.id =:itemId")
    List<OrderItem> findByItemId(long itemId);
}
