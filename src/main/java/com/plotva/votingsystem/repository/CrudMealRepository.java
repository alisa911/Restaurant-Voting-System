package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.Meal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal save(Meal meal);

    Meal getMealById(int id);

    int deleteMealById(int id);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.restaurant WHERE m.id=:id")
    Meal getMealByIdWithRestaurant(@Param("id") int id);

    List<Meal> getMealsByRestaurantId(int restaurantId, Sort sort);
}
