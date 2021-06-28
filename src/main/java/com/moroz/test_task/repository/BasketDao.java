package com.moroz.test_task.repository;

import com.moroz.test_task.model.Basket;
import com.moroz.test_task.model.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketDao extends JpaRepository<Basket, Long> {
}
