package com.plotva.votingsystem.to;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VoteTo extends BaseTo {
    private int restaurantId;

    @NotNull
    private LocalDate votingDate = LocalDate.now();

    private int userId;

    public VoteTo() {
    }

    public VoteTo(VoteTo vote) {
        this(vote.getId(), vote.getRestaurantId(), vote.getVotingDate());
    }

    public VoteTo(Integer id, int restaurantId, LocalDate votingDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.votingDate = votingDate;
    }

    public VoteTo(Integer id, int restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getVotingDate() {
        return votingDate;
    }

    public void setDate(LocalDate votingDate) {
        this.votingDate = votingDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "restaurantId=" + restaurantId +
                ", votingDate=" + votingDate +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
