package com.plotva.votingsystem.repository;

import com.plotva.votingsystem.model.User;
import com.plotva.votingsystem.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public class VoteRepository {
    @Autowired
    private CrudVoteRepository repository;

    @Autowired
    private CrudUserRepository userRepository;

    public Vote save(Vote vote, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        vote.setUser(user);

        if (vote.isNew()) {
            return repository.save(vote);
        } else {
            return get(vote.getId(), userId) == null ? null : repository.save(vote);
        }
    }

    public Vote get(int id, int userId) {
        return repository.getVoteByIdAndUser_id(id, userId);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAllByUser_id(userId);
    }

    public int getCount(int restaurantId, LocalDate date) {
        return repository.getCount(restaurantId, date);
    }

    public boolean delete(int id, int userId) {
        return repository.deleteVoteByIdAndUser_id(id, userId) != 0;
    }


}
