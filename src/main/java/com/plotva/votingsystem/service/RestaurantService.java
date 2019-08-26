package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Restaurant;
import com.plotva.votingsystem.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must be not null");
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must be not null");
        repository.save(restaurant);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

}
