package com.plotva.votingsystem.web;

import com.plotva.votingsystem.model.Meal;
import com.plotva.votingsystem.service.MealService;
import com.plotva.votingsystem.to.MealRestaurantTo;
import com.plotva.votingsystem.to.MealTo;
import com.plotva.votingsystem.util.JsonUtil;
import com.plotva.votingsystem.web.controller.MealRestController;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.plotva.votingsystem.data.MealTestData.*;
import static com.plotva.votingsystem.UtilTest.*;
import static com.plotva.votingsystem.data.RestaurantTestData.*;
import static com.plotva.votingsystem.data.UserTestData.FIRST_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";
    @Autowired
    private MealService service;

    @Test
    void testGet() throws Exception {
        MealTo meal = modelMapper.map(FIRST_MEAL, MealTo.class);
        mockMvc.perform(get(REST_URL + FIRST_MEAL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(meal));
    }

    @Test
    void getAll() throws Exception {
        List<MealTo> expected = modelMapper.map(Arrays.asList(FOURTH_MEAL, FIFTH_MEAL, SIXTH_MEAL), new TypeToken<List<MealTo>>() {}.getType());

        mockMvc.perform(get(REST_URL + "all/" + THIRD_RESTAURANT_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(expected))
                .andExpect(status().isOk());
    }

    @Test
    void getAllForDate() throws Exception {
        List<MealTo> expected = Collections.singletonList(modelMapper.map(FIRST_MEAL, MealTo.class));

        mockMvc.perform(get(REST_URL + "all/" + FIRST_RESTAURANT_ID + "?date=" + LocalDate.of(2019, 8, 19)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(expected));
    }

    @Test
    void testGetWithRestaurant() throws Exception {
        Meal meal = new Meal(FOURTH_MEAL);
        meal.setRestaurant(THIRD_RESTAURANT);
        meal.getRestaurant().setMenu(THIRD_RESTAURANT_MENU);
        MealRestaurantTo expected = modelMapper.map(FOURTH_MEAL, MealRestaurantTo.class);

        mockMvc.perform(get(REST_URL + "with/" + FOURTH_MEAL_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(expected));
    }

    @Test
    void testUpdate() throws Exception {
        Meal meal = new Meal(FIRST_MEAL);
        meal.setName("New meal");
        meal.setPrice(10);

        MealTo updated = modelMapper.map(meal, MealTo.class);

        mockMvc.perform(put(REST_URL + FIRST_MEAL_ID)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatchMealTo(modelMapper.map(service.get(FIRST_MEAL_ID), MealTo.class), updated);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + FOURTH_MEAL_ID)
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(THIRD_RESTAURANT_ID), FIFTH_MEAL, SIXTH_MEAL);
    }

    @Test
    void create() throws Exception {
        Meal meal = new Meal(null, "Fresh", FIRST_RESTAURANT, 90, LocalDate.of(2019, 9, 4));
        MealTo expected = modelMapper.map(meal, MealTo.class);
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        MealTo returned = readFromJson(actions, MealTo.class);
        expected.setId(returned.getId());

        List<MealTo> expectedList = modelMapper.map(Arrays.asList(expected, FIRST_MEAL), new TypeToken<List<MealTo>>() {}.getType());

        assertMatchMealTo(returned, expected);
        assertMatchMealTo(modelMapper.map(service.getAll(FIRST_RESTAURANT_ID), new TypeToken<List<MealTo>>() {}.getType()), expectedList);
    }

}