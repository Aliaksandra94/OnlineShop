package com.moroz.test_task.repository;

import com.moroz.test_task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query(value = "select u from User u where u.id =:id")
    User findById(long id);
}
