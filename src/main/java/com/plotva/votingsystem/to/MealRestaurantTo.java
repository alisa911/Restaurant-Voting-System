package com.plotva.votingsystem.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class MealRestaurantTo extends BaseTo {
    @NotBlank
    private String name;

    private int price;

    private RestaurantTo restaurant;

    @NotNull
    private LocalDate date = LocalDate.now();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public RestaurantTo getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantTo restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MealRestaurantTo{" +
                "name='" + name + '\'' +
                ", restaurant=" + restaurant +
                ", price=" + price +
                ", date=" + date +
                ", id=" + id +
                '}';
    }

}
