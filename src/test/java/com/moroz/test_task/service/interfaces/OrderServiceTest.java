package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.*;
import com.moroz.test_task.repository.ItemDAO;
import com.moroz.test_task.repository.OrderDAO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderDAO orderDAO;
    @Test
    void returnOrderById() {
        long id = 1;
        Order order = new Order(new User("SASHA", "SASHA", "SASHA"));
        Mockito.doReturn(new Order(new User("SASHA", "SASHA", "SASHA"))).when(orderDAO).getById(id);
        Assert.assertEquals(order, orderService.returnOrderById(id));
        Assert.assertNotNull(orderService.returnOrderById(id).getOrderItems());
    }
}