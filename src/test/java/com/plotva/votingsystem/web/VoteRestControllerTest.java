package com.plotva.votingsystem.web;

import com.plotva.votingsystem.model.Vote;
import com.plotva.votingsystem.service.VoteService;
import com.plotva.votingsystem.to.VoteTo;
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
import static com.plotva.votingsystem.data.RestaurantDataUtil.*;
import static com.plotva.votingsystem.data.UserDataUtil.FIRST_USER;
import static com.plotva.votingsystem.data.UserDataUtil.FIRST_USER_ID;
import static com.plotva.votingsystem.data.VoteDataUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.plotva.votingsystem.web.controller.VoteRestController.REST_URL;

public class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService service;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + FIRST_VOTE_ID)
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVoteTo(modelMapper.map(FIRST_VOTE, VoteTo.class)));
    }

    @Test
    void getAll() throws Exception {
        List<VoteTo> expected = modelMapper.map(Arrays.asList(FIRST_VOTE, SECOND_VOTE), new TypeToken<List<VoteTo>>() {
        }.getType());

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(FIRST_USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonVoteTo(expected));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + FIRST_VOTE_ID)
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(FIRST_USER_ID), SECOND_VOTE);
    }

    @Test
    void create() throws Exception {
        VoteTo expected = new VoteTo(null, SECOND_RESTAURANT_ID);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        VoteTo returned = readFromJson(actions, VoteTo.class);
        expected.setId(returned.getId());
        expected.setUserId(returned.getUserId());

        Vote expectedVote = modelMapper.map(expected, Vote.class);
        expectedVote.setRestaurant(SECOND_RESTAURANT);

        assertMatchVoteTo(returned, expected);
        assertMatch(service.getAll(FIRST_USER_ID), FIRST_VOTE, SECOND_VOTE, expectedVote);
    }

    @Test
    void update() throws Exception {
        VoteTo updated = modelMapper.map(FIRST_VOTE, VoteTo.class);
        updated.setRestaurantId(FIRST_RESTAURANT.getId());

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + FIRST_VOTE_ID)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        VoteTo expected = modelMapper.map(service.get(FIRST_VOTE_ID, FIRST_USER_ID), VoteTo.class);
        assertMatchVoteTo(expected, updated);
    }
}
