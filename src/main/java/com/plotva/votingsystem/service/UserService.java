package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.checkNotFound;
import static com.plotva.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return userRepository.save(user);
    }


    public void delete(int id) {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must be not null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }


    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

}
