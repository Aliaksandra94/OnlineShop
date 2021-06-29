package com.moroz.test_task.service;

import com.moroz.test_task.model.Basket;
import com.moroz.test_task.model.Role;
import com.moroz.test_task.model.User;
import com.moroz.test_task.repository.RoleDao;
import com.moroz.test_task.repository.UserDao;
import com.moroz.test_task.service.interfaces.UserService;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(String login, String password, String email) {
        User user = new User(login, passwordEncoder.encode(password), email);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById((long) 2));
        user.setUsersRole(roles);
        user.setBasket(new Basket(user, new ArrayList<>()));
        userDao.save(user);
        return user;
    }

    @Override
    public User returnUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public List<User> returnUsersByRoleId(long roleId) {
        return userDao.findByUsersRole(roleDao.getById(roleId));
    }

    @Override
    public User checkUserForLogIn(String login, String password) {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            if (!(user.getPassword()).equals(password)) {
                throw new UsernameNotFoundException("Wrong password");
            }
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.fromUser(user);
    }
}
