package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Meal;
import com.plotva.votingsystem.repository.MealRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "meals", allEntries = true)
    public Meal create(Meal meal) {
        Assert.notNull(meal, "Meal must be not null");
        return repository.save(meal);
    }

    public List<Meal> create(List<Meal> meal) {
        Assert.notNull(meal, "Meals list must be not null");
        return repository.save(meal);
    }

    @CacheEvict(value = "meals", allEntries = true)
    public void update(Meal meal) {
        Assert.notNull(meal, "Meal must be not null");
        repository.save(meal);
    }

    @Cacheable("meals")
    public List<Meal> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public Meal get(int id) {

        return checkNotFoundWithId(repository.get(id), id);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal getWithRestaurant(int id) {
        return checkNotFoundWithId(repository.getWithRestaurant(id), id);
    }

    public List<Meal> getAllByDate(int restaurantId, LocalDate date) {
        Assert.notNull(date, "Date must be not null");
        return repository.getAllByDate(restaurantId, date);
    }
}
