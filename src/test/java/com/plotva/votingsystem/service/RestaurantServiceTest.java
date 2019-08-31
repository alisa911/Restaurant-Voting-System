package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Restaurant;
import com.plotva.votingsystem.repository.JpaUtil;
import com.plotva.votingsystem.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.plotva.votingsystem.data.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("meals").clear();
        jpaUtil.clear2ndLevelCache();
    }

    @Test
    void create() {
        Restaurant restaurant = new Restaurant(null, "Fish");
        Restaurant created = service.create(restaurant);
        restaurant.setId(created.getId());
        assertMatch(service.getAll(), restaurant, FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void update() {
        Restaurant restaurant = new Restaurant(FIRST_RESTAURANT);
        restaurant.setName("New Restaurant");
        service.update(restaurant);
        assertMatch(service.getAll(), restaurant, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void get() {
        assertMatch(service.get(FIRST_RESTAURANT_ID), FIRST_RESTAURANT);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(), FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(10000));
    }

    @Test
    void delete() {
        service.delete(FIRST_RESTAURANT_ID);
        assertMatch(service.getAll(), SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(10000));
    }
}
