package com.moroz.test_task.service.interfaces;

import com.moroz.test_task.model.Role;
import com.moroz.test_task.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User addNewUser(String login, String password, String email);
    User returnUserByLogin(String login);
    User checkUserForLogIn(String login, String password);
    List<User> returnUsersByRoleId(long roleId);
}
