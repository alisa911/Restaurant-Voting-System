package com.plotva.votingsystem.to;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RestaurantVoteTo extends BaseTo {
    @NotBlank
    private String name;

    private List<MealTo> menu;

    private int votes;

    public RestaurantVoteTo() {
    }

    public RestaurantVoteTo(Integer id, String name, List<MealTo> menu, int votes) {
        super(id);
        this.name = name;
        this.menu = menu;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealTo> getMenu() {
        return menu;
    }

    public void setMenu(List<MealTo> menu) {
        this.menu = menu;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "RestaurantToWithVotes{" +
                "name='" + name + '\'' +
                ", menu=" + menu +
                ", votes=" + votes +
                ", id=" + id +
                '}';
    }
}
