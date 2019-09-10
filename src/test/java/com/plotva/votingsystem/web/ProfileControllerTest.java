package com.plotva.votingsystem.web;

import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.to.UserTo;
import com.plotva.votingsystem.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.plotva.votingsystem.UtilTest.*;
import static com.plotva.votingsystem.data.UserUtilData.*;
import static com.plotva.votingsystem.web.controller.user.ProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileControllerTest extends AbstractControllerTest {

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/")
                .with(userAuth(SECOND_USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(SECOND_USER));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/")
                .with(userAuth(SECOND_USER)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), FIRST_USER);
    }

    @Test
    void register() throws Exception {
        UserTo userTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(userTo, userTo.getPassword())))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);

        User created = readFromJson(action, User.class);
        created.setName(userTo.getName());
        created.setEmail(userTo.getEmail());

        assertMatch(returned, created);
        assertMatch(userService.getByEmail("newemail@ya.ru"), created);
    }

    @Test
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(FIRST_USER))
                .content(jsonWithPassword(updatedTo, updatedTo.getPassword())))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(FIRST_USER), updatedTo));
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", "nullrtr");

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userAuth(SECOND_USER))
                .content(jsonWithPassword(updatedTo, updatedTo.getPassword())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
