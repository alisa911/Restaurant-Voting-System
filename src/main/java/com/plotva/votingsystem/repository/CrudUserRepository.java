package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudUserRepository extends JpaRepository<User, Integer> {

    int deleteUserById(int id);

    User getUserByEmail(String email);
}
