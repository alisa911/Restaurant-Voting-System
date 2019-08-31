package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.Meal;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MealRepository {
    private static final Sort SORT_BY_DATE = new Sort(Sort.Direction.DESC, "date");

    private final CrudMealRepository repository;


    public MealRepository(CrudMealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    public List<Meal> save(List<Meal> meals) {
        return repository.saveAll(meals);
    }

    public Meal get(int id) {
        return repository.getMealById(id);
    }

    public List<Meal> getAll(int restaurantId) {
        return repository.getMealsByRestaurantId(restaurantId, SORT_BY_DATE);
    }

    public Meal getWithRestaurant(int id) {
        return repository.getMealByIdWithRestaurant(id);
    }

    public List<Meal> getAllByDate(int restaurantId, LocalDate date) {
        return repository.getMealsByRestaurantIdAndDate(restaurantId, date);
    }

    public boolean delete(int id) {
        return repository.deleteMealById(id) != 0;
    }

}
