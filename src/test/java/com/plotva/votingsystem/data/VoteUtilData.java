package com.plotva.votingsystem.data;

import com.plotva.votingsystem.model.Vote;
import com.plotva.votingsystem.to.VoteTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.plotva.votingsystem.UtilTest.readFromJsonMvcResult;
import static com.plotva.votingsystem.UtilTest.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

import static com.plotva.votingsystem.data.RestaurantUtilData.FIRST_RESTAURANT;
import static com.plotva.votingsystem.data.RestaurantUtilData.SECOND_RESTAURANT;
import static com.plotva.votingsystem.data.UserUtilData.FIRST_USER;

public class VoteUtilData {

    public static final int FIRST_VOTE_ID = 1011;
    public static final Vote FIRST_VOTE = new Vote(FIRST_VOTE_ID, FIRST_USER, SECOND_RESTAURANT, LocalDate.of(2019, 8, 20));
    public static final Vote SECOND_VOTE = new Vote(FIRST_VOTE_ID + 1, FIRST_USER, FIRST_RESTAURANT, LocalDate.of(2019, 8, 21));

    private VoteUtilData() {
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual.getUser()).isEqualToIgnoringGivenFields(expected.getUser(), "registered", "password");
        assertThat(actual.getRestaurant()).isEqualToIgnoringGivenFields(expected.getRestaurant(), "menu");
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
    }

    public static void assertMatchVoteTo(Iterable<VoteTo> actual, Iterable<VoteTo> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatchVoteTo(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static ResultMatcher contentJsonVoteTo(List<VoteTo> expected) {
        return result -> assertMatchVoteTo(readListFromJsonMvcResult(result, VoteTo.class), expected);
    }

    public static ResultMatcher contentJsonVoteTo(VoteTo expected) {
        return result -> assertMatchVoteTo(readFromJsonMvcResult(result, VoteTo.class), expected);
    }


}
