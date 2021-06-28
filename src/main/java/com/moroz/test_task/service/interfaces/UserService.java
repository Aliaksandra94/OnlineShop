package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void addNewUser(String login, String password, String email);
    User returnUserByLogin(String login);
    User checkUserForLogIn(String login, String password);
}
