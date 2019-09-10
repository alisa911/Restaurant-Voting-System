package com.plotva.votingsystem.service;

import com.plotva.votingsystem.AuthorizedUser;
import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.repository.UserRepository;
import com.plotva.votingsystem.to.UserTo;
import com.plotva.votingsystem.util.UserUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.plotva.votingsystem.util.UserUtil.prepareToSave;
import static com.plotva.votingsystem.util.ValidationUtil.checkNotFound;
import static com.plotva.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "users", allEntries = true)
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

    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        userRepository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        Assert.notNull(userTo, "UserTo must be not null");
        User user = get(userTo.getId());
        User updated = UserUtil.updateFromTo(user, userTo);
        userRepository.save(prepareToSave(updated, passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null)
            throw new UsernameNotFoundException(String.format("User %s is not found", email));
        return new AuthorizedUser(user);
    }

}
