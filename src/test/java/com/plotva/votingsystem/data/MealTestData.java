package com.plotva.votingsystem.data;

import com.plotva.votingsystem.model.Meal;
import com.plotva.votingsystem.to.MealRestaurantTo;
import com.plotva.votingsystem.to.MealTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.plotva.votingsystem.data.RestaurantTestData.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int FIRST_MEAL_ID = 1005;
    public static final int FOURTH_MEAL_ID = 1008;
    public static final Meal FIRST_MEAL = new Meal(FIRST_MEAL_ID, "Fish", FIRST_RESTAURANT, 10,LocalDate.of(2019, 8, 19));
    public static final Meal SECOND_MEAL = new Meal(FIRST_MEAL_ID + 1, "Salad", SECOND_RESTAURANT, 5, LocalDate.of(2019, 8, 19));
    public static final Meal THIRD_MEAL = new Meal(FIRST_MEAL_ID + 2, "Water", SECOND_RESTAURANT, 1, LocalDate.of(2019, 8, 19));
    public static final Meal FOURTH_MEAL = new Meal(FOURTH_MEAL_ID, "Burger", THIRD_RESTAURANT, 40, LocalDate.of(2019, 8, 18));
    public static final Meal FIFTH_MEAL = new Meal(FIRST_MEAL_ID + 4, "Potato", THIRD_RESTAURANT, 10, LocalDate.of(2019, 8, 18));
    public static final Meal SIXTH_MEAL = new Meal(FIRST_MEAL_ID + 5, "IceCream", THIRD_RESTAURANT, 20, LocalDate.of(2019, 8, 18));
    public static final List<Meal> THIRD_RESTAURANT_MENU = Arrays.asList(FOURTH_MEAL, FIFTH_MEAL, SIXTH_MEAL);

    private MealTestData() {
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatchToWithRestaurant(MealRestaurantTo actual, MealRestaurantTo expected) {
        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatchWithRestaurant(Meal actual, Meal expected) {
        assertMatch(actual, expected);
        assertThat(actual.getRestaurant()).isEqualToComparingFieldByFieldRecursively(expected.getRestaurant());
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static void assertMatchMealTo(Iterable<MealTo> actual, Iterable<MealTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static void assertMatchMealTo(MealTo actual, MealTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }
}
