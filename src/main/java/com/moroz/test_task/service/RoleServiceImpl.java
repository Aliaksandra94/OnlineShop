package com.moroz.test_task.service;

import com.moroz.test_task.model.Role;
import com.moroz.test_task.repository.RoleDao;
import com.moroz.test_task.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> returnAllRoles() {
        return roleDao.findAll();
    }
}
