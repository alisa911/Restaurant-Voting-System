package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Vote;
import com.plotva.votingsystem.util.NotFoundException;
import com.plotva.votingsystem.util.TimeOverException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.plotva.votingsystem.data.RestaurantTestData.*;
import static com.plotva.votingsystem.data.UserTestData.*;
import static com.plotva.votingsystem.data.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() {
        Vote newVote = new Vote(null, null, FIRST_RESTAURANT, LocalDate.now());
        Vote created = voteService.create(newVote, FIRST_USER_ID);
        newVote.setId(created.getId());
        newVote.setUser(created.getUser());
        List<Vote> all = voteService.getAll(FIRST_USER_ID);
        assertMatch(all, FIRST_VOTE, SECOND_VOTE, newVote);
    }

    @Test
    void delete() {
        assumeFalse(LocalTime.now().isAfter(LocalTime.of(23, 0)), "Before 11 pm");
        voteService.delete(FIRST_VOTE_ID, FIRST_USER_ID);
        assertMatch(voteService.getAll(FIRST_USER_ID), SECOND_VOTE);
    }

    @Test
    void get() {
        Vote vote = new Vote(FIRST_VOTE);
        vote.setUser(FIRST_USER);
        assertMatch(voteService.get(FIRST_VOTE_ID, FIRST_USER_ID), vote);
    }

    @Test
    void getAll() {
        assertMatch(voteService.getAll(FIRST_USER_ID), FIRST_VOTE, SECOND_VOTE);
    }

    @Test
    void update() {
        assumeFalse(LocalTime.now().isAfter(LocalTime.of(23, 0)), "Before 11 pm");
        Vote vote = new Vote(SECOND_VOTE);
        vote.setRestaurant(SECOND_RESTAURANT);
        voteService.update(vote, FIRST_USER_ID);
        assertMatch(voteService.getAll(FIRST_USER_ID), FIRST_VOTE, vote);
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> voteService.get(FIRST_VOTE_ID, SECOND_USER.getId()));
    }

    @Test
    void updateNotOwn() {
        assumeFalse(LocalTime.now().isAfter(LocalTime.of(23, 0)), "Before 11 pm");
        assertThrows(NotFoundException.class, () -> voteService.update(FIRST_VOTE, SECOND_USER.getId()));
    }

    @Test
    void getCount() {
        assertEquals(voteService.getCount(FIRST_RESTAURANT_ID, LocalDate.of(2019, 8, 20)), 1);
    }

    @Test
    void twoVoteInOneDay() {
        LocalDate date = LocalDate.of(2019, 8, 20);
        Vote firstVote = new Vote(null, FIRST_USER, FIRST_RESTAURANT, date);
        Vote secondVote = new Vote(null, FIRST_USER, FIRST_RESTAURANT, date);
        voteService.create(firstVote, FIRST_USER_ID);
        assertThrows(DataIntegrityViolationException.class, () -> voteService.create(secondVote, FIRST_USER_ID));
        assertMatch(voteService.getAll(FIRST_USER_ID), FIRST_VOTE, SECOND_VOTE, firstVote);
    }

    @Test
    void updateTimeOver() {
        assumeTrue(LocalTime.now().isAfter(LocalTime.of(23, 0)), "After 11 pm");
        assertThrows(TimeOverException.class, () -> voteService.update(FIRST_VOTE, FIRST_USER_ID));
    }
}
