package com.plotva.votingsystem.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class MealTo extends BaseTo {
    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

    private int restaurantId;

    private int price;


    @NotNull
    private LocalDate date = LocalDate.now();

    public MealTo() {
    }

    public MealTo(Integer id, String name, int restaurantId, int price, LocalDate date) {
        super(id);
        this.name = name;
        this.restaurantId = restaurantId;
        this.price = price;
        this.date = date;
    }

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

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurant) {
        this.restaurantId = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "  id=" + id +
                ", name='" + name +
                ", restaurantId=" + restaurantId +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
