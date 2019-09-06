package com.plotva.votingsystem.web;

import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

import static com.plotva.votingsystem.UtilTest.readFromJson;
import static com.plotva.votingsystem.UtilTest.userAuth;
import static com.plotva.votingsystem.data.UserTestData.*;
import static com.plotva.votingsystem.model.Role.ROLE_USER;
import static com.plotva.votingsystem.web.controller.userController.AdminRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + FIRST_USER_ID)
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_USER));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(FIRST_USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_USER, SECOND_USER));
    }

    @Test
    void getByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/by?email=" + FIRST_USER.getEmail())
                .with(userAuth(FIRST_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(FIRST_USER));
    }

    @Test
    void create() throws Exception {
        User expected = new User(null, "NewUser", new Date(),"new@gmail.com", "newPass", true, Set.of(ROLE_USER));
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, expected.getPassword())))
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), expected, FIRST_USER, SECOND_USER);
    }

    @Test
    void update() throws Exception {
        User updated = new User(FIRST_USER);
        updated.setName("UpdatingName");
        updated.setEmail("updatingemail@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + FIRST_USER_ID)
                .with(userAuth(FIRST_USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, updated.getPassword())))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(FIRST_USER_ID), updated);
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + FIRST_USER_ID)
                .with(userAuth(FIRST_USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), SECOND_USER);
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userAuth(SECOND_USER)))
                .andExpect(status().isForbidden());
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createInvalid() throws Exception {
        User expected = new User(null, "New", new Date(), null, "newPass", true, Set.of(ROLE_USER));
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(FIRST_USER))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateInvalid() throws Exception {
        User updated = new User(FIRST_USER);
        updated.setName("");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + FIRST_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(FIRST_USER))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}
