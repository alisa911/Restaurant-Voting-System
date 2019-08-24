package com.plotva.votingsystem.service;

import com.plotva.votingsystem.model.Vote;
import com.plotva.votingsystem.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.*;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(voteRepository.get(id, userId), id);
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "Vote must be not null");
        checkForSameDate(vote.getVotingDate(), get(vote.getId(), userId).getVotingDate(), "You can not change voting date");
        checkNotFoundWithId(voteRepository.save(vote, userId), vote.getId());
    }

    public int getCount(int restaurantId, LocalDate date) {
        return voteRepository.getCount(restaurantId, date);
    }

    public Vote create(Vote vote, int userId) {
        vote.setVotingDate(LocalDate.now());
        Assert.notNull(vote, "Vote must be not null");
        return voteRepository.save(vote, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId), id);
    }

}
