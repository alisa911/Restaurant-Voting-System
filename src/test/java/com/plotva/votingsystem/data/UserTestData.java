package com.plotva.votingsystem.data;

import com.plotva.votingsystem.model.Role;
import com.plotva.votingsystem.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int FIRST_USER_ID = 1000;
    public static final String FIRST_USER_EMAIL = "qweqwe@gmail.com";
    public static final User FIRST_USER = new User(FIRST_USER_ID, "Nataly", new Date(), FIRST_USER_EMAIL, "qweqwe", true, Collections.singleton(Role.ROLE_ADMIN));
    public static final User SECOND_USER = new User(FIRST_USER_ID + 1, "Alisa", new Date(), "asdasd@gmail.com", "asdasd911", true, Collections.singleton(Role.ROLE_USER));

    private UserTestData() {
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered").isEqualTo(expected);
    }

}