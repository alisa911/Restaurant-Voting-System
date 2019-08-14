package com.plotva.votingsystem.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "voting_date"}, name = "users_votes_unique_date_idx"))
public class Vote extends AbstractBaseEntity {

    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 1000"))
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @JoinColumn(name = "restaurant_id", foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 1000"))
    @OneToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @Column(name = "voting_date", nullable = false, columnDefinition = "DATE DEFAULT now()")
    private LocalDate votingDate;

    public Vote() {
    }

    public Vote(Vote v) {
        this(v.getId(), v.getUser(), v.getRestaurant(), v.getVotingDate());
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate votingDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.votingDate = votingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(LocalDate votingDate) {
        this.votingDate = votingDate;
    }

    @Override
    public String toString() {
        return "Vote{" +
                ", id=" + id +
                "user=" + user +
                ", restaurant=" + restaurant +
                ", votingDate=" + votingDate +
                '}';
    }
}
