package org.lab.service;

import org.lab.model.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private Map<String, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    public User registerUser(String fullName) {

        String id = UUID.randomUUID().toString();
        User user = new User(fullName);
        user.setId(id);

        users.put(id, user);

        return user;

    }


}
