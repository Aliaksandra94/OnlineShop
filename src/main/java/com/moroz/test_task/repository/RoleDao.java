package com.moroz.test_task.repository;

import com.moroz.test_task.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    @Query(value = "select * from ROLE where id=:id", nativeQuery = true)
    Role findById(long id);
}
