package com.plotva.votingsystem.data;

import com.plotva.votingsystem.model.Role;
import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.to.UserTo;
import com.plotva.votingsystem.util.JsonUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.Collections;

import java.util.Date;
import java.util.List;


import static com.plotva.votingsystem.UtilTest.readFromJsonMvcResult;
import static com.plotva.votingsystem.UtilTest.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int FIRST_USER_ID = 1000;
    public static final String FIRST_USER_EMAIL = "qweqwe@gmail.com";
    public static final User FIRST_USER = new User(FIRST_USER_ID, "Nataly", new Date(), FIRST_USER_EMAIL, "admin", true, Collections.singleton(Role.ROLE_ADMIN));
    public static final User SECOND_USER = new User(FIRST_USER_ID + 1, "Alisa", new Date(), "asdasd@gmail.com", "user", true, Collections.singleton(Role.ROLE_USER));

    private UserTestData() {
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static String jsonWithPassword(UserTo user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

}