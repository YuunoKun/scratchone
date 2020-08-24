package me.scratchone.service;

import me.scratchone.domain.RegistrationState;
import me.scratchone.domain.User;

public interface UserService {

    RegistrationState register(User user);

    void activate(String code);

    User login(User user);

    User getProfile(String username);

    void updateProfile(User user);

    User getUserProfileByUid(int uid);
}
