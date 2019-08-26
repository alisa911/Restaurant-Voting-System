package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RestaurantRepository {
    private static final Sort SORT_BY_NAME = new Sort(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository repository;

    public RestaurantRepository(CrudRestaurantRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return repository.getById(id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_BY_NAME);
    }

    @Transactional
    public boolean delete(int id) {
        return repository.deleteMealById(id) != 0;
    }
}
