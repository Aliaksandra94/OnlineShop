package com.moroz.test_task.repository;

import com.moroz.test_task.model.Item;
import com.moroz.test_task.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemDAO extends JpaRepository<Item, Long> {
    @Query(value = "from Item item where lower(item.description) like lower(concat('%',:description,'%'))")
    List<Item> findByDescription(String description);
    List<Item> findByTags(Tag tag);
}
