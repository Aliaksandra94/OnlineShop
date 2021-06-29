package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.User;
import com.moroz.test_task.repository.RoleDao;
import com.moroz.test_task.repository.UserDao;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    private UserDao userDao;
    @MockBean
    private RoleDao roleDao;
    @MockBean
    private PasswordEncoder passwordEncoder;


    @Test
    void addNewUser() {
        User userActual = new User("SASHA", passwordEncoder.encode("SASHA"), "SASHA");
        User userExpected = userService.addNewUser("SASHA", "SASHA", "SASHA");
        Assert.assertNotNull(userExpected);
        Assert.assertEquals(userActual.getLogin(), userExpected.getLogin());
        Assert.assertEquals(userActual.getEmail(), userExpected.getEmail());
        Assert.assertEquals(userActual.getPassword(), userExpected.getPassword());
        Assert.assertEquals(userActual, userExpected);
        Assert.assertNotNull(userExpected.getBasket());
        Assert.assertNotNull(userExpected.getOrders());
        Mockito.verify(roleDao, Mockito.times(1)).getById((long) 2);
        Mockito.verify(userDao, Mockito.times(1)).save(userExpected);
    }

    @Test
    void addNewUser_NOT_NULL() {
        User user = new User("SASHA", passwordEncoder.encode("SASHA"), "SASHA");
        Assert.assertNotNull(user);
    }

    @Test
    void findUserByUserName() {
        User userActual = new User("SASHA", passwordEncoder.encode("SASHA"), "SASHA");
        Mockito.doReturn(new User("SASHA", passwordEncoder.encode("SASHA"), "SASHA")).when(userDao).findByLogin("SASHA");
        User userExpected = userService.returnUserByLogin("SASHA");
        Assert.assertEquals(userActual, userExpected);
    }
}