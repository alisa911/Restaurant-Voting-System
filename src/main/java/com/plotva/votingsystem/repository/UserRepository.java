package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public class UserRepository {

    private static final Sort SORT_BY_DATE = new Sort(Sort.Direction.DESC, "registered");

    private final CrudUserRepository crudRepository;

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User save(User user) {
        return crudRepository.save(user);
    }

    public User get(int id) {
        return crudRepository.getById(id);
    }

    public boolean delete(int id) {
        return crudRepository.deleteUserById(id) != 0;
    }

    public User getByEmail(String email) {
        return crudRepository.getUserByEmail(email);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_BY_DATE);
    }
}