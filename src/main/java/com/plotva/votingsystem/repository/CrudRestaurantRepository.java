package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    int deleteMealById(int id);

    Restaurant getById(int id);
}
