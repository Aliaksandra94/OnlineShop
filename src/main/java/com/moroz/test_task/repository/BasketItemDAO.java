package com.moroz.test_task.repository;

import com.moroz.test_task.model.BasketItem;
import com.moroz.test_task.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemDAO extends JpaRepository<BasketItem, Long> {
    List<BasketItem> findByBasketId(long basketId);
    BasketItem findByItems(Item item);
}
