package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Meal;
import com.plotva.votingsystem.repository.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.plotva.votingsystem.data.MealTestData.*;
import static com.plotva.votingsystem.data.RestaurantTestData.*;

public class MealServiceTest extends AbstractServiceTest {
    @Autowired
    private MealService service;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("meals").clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void create() {
        Meal meal = new Meal(null, "meal test", FIRST_RESTAURANT, 10, LocalDate.of(2019, 8, 26));
        Meal created = service.create(meal);
        meal.setId(created.getId());
        assertMatch(service.getAll(FIRST_RESTAURANT_ID), meal, FIRST_MEAL);
    }

    @Test
    void createAll() {
        Meal mealFirst = new Meal(null, "meal test 1", FIRST_RESTAURANT, 10, LocalDate.of(2019, 8, 26));
        Meal mealSecond = new Meal(null, "meal test 2", FIRST_RESTAURANT, 15, LocalDate.of(2019, 8, 26));
        List<Meal> created = service.create(Arrays.asList(mealFirst, mealSecond));

        mealFirst.setId(created.get(0).getId());
        mealSecond.setId(created.get(1).getId());

        assertMatch(service.getAll(FIRST_RESTAURANT_ID), mealFirst, mealSecond, FIRST_MEAL);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_MEAL_ID), FIRST_MEAL);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(THIRD_RESTAURANT_ID), FOURTH_MEAL, FIFTH_MEAL, SIXTH_MEAL);
    }

    @Test
    void getWithRestaurant() {
        Meal expected = new Meal(FOURTH_MEAL);
        assert expected.getRestaurant() != null;
        expected.getRestaurant().setMenu(THIRD_RESTAURANT_MENU);
        assertMatchWithRestaurant(service.getWithRestaurant(FOURTH_MEAL_ID), expected);
    }

    @Test
    void update() {
        Meal meal = new Meal(FIRST_MEAL);
        meal.setPrice(100);
        service.update(meal);
        assertMatch(service.getAll(FIRST_RESTAURANT_ID), meal);
    }

    @Test
    void delete() {
        service.delete(FOURTH_MEAL_ID);
        assertMatch(service.getAll(THIRD_RESTAURANT_ID), FIFTH_MEAL, SIXTH_MEAL);
    }
}
