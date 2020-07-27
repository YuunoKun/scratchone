package me.scratchone.dao;

import me.scratchone.domain.User;

public interface UserDao {

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User findUserByUsernameAndPassword(String username, String password);

    void createNewUser(User user);

    void updateUserStatusByCode(String code);

    void updateUserByProfile(User user);
}
