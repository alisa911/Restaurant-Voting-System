package com.plotva.votingsystem.web;

import com.plotva.votingsystem.model.Restaurant;
import com.plotva.votingsystem.service.RestaurantService;
import com.plotva.votingsystem.to.RestaurantTo;
import com.plotva.votingsystem.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static com.plotva.votingsystem.UtilTest.readFromJson;
import static com.plotva.votingsystem.UtilTest.userAuth;
import static com.plotva.votingsystem.data.RestaurantUtilData.*;
import static com.plotva.votingsystem.data.UserUtilData.FIRST_USER;
import static com.plotva.votingsystem.web.controller.RestaurantRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    @Test
    void get() throws Exception {
        Restaurant expected = new Restaurant(THIRD_RESTAURANT);

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + THIRD_RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(modelMapper.map(expected, RestaurantTo.class)));
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantTo> expected = modelMapper.map(Arrays.asList(FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT), new TypeToken<List<RestaurantTo>>() {
        }.getType());
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + "all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(expected));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + FIRST_RESTAURANT_ID)
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isNoContent());

        assertMatch(service.getAll(), SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void create() throws Exception {
        Restaurant expected = new Restaurant(null, "New Restaurant");
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = readFromJson(actions, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(service.getAll(), expected, FIRST_RESTAURANT, SECOND_RESTAURANT, THIRD_RESTAURANT);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = new Restaurant(FIRST_RESTAURANT);
        updated.setName("Updated Name");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + FIRST_RESTAURANT_ID)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(FIRST_RESTAURANT_ID), updated);
    }
}
