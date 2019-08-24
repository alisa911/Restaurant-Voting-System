package com.plotva.votingsystem.to;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RestaurantTo extends BaseTo {

    @NotBlank
    private String name;

    private List<MealTo> menu;

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

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                ", menu=" + menu +
                ", id=" + id +
                '}';
    }
}
