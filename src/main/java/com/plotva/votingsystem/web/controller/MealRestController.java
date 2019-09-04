package com.plotva.votingsystem.web.controller;

import com.plotva.votingsystem.model.Meal;
import com.plotva.votingsystem.service.MealService;
import com.plotva.votingsystem.to.MealRestaurantTo;
import com.plotva.votingsystem.to.MealTo;
import com.plotva.votingsystem.web.View;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.plotva.votingsystem.util.ValidationUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.plotva.votingsystem.util.ValidationUtil.checkNew;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController {
    public static final String REST_URL = "/meals";
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    private final ModelMapper modelMapper;

    public MealRestController(MealService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MealTo> createWithLocation(@Validated(View.Web.class) @RequestBody MealTo meal) {
        MealTo created = create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<MealTo> createAllWithLocation(@Validated(View.Web.class) @RequestBody List<MealTo> meals) {
        return createAll(meals);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody MealTo meal, @PathVariable int id) {
        assureIdConsistent(meal, id);
        log.info("Update {}", meal);
        service.update(modelMapper.map(meal, Meal.class));
    }

    @GetMapping("/all/{restaurant}")
    public List<MealTo> getAll(@PathVariable int restaurant, @DateTimeFormat(iso = DATE) @RequestParam(required = false) LocalDate date) {
        return date == null ? getAll(restaurant) : getAllByDate(restaurant, date);
    }

    @GetMapping("/{id}")
    public MealTo get(@PathVariable int id) {
        log.info("Get meal with id {}", id);
        return modelMapper.map(service.get(id), MealTo.class);
    }

    @GetMapping("/with/{id}")
    public MealRestaurantTo getWithRestaurant(@PathVariable int id) {
        log.info("Get meal with restaurant and id {}", id);
        return modelMapper.map(service.getWithRestaurant(id), MealRestaurantTo.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete meal with id {}", id);
        service.delete(id);
    }

    private MealTo create(MealTo mealTo) {
        checkNew(mealTo);
        log.info("Create {}", mealTo);
        Meal meal = modelMapper.map(mealTo, Meal.class);
        Meal created = service.create(meal);
        return modelMapper.map(created, MealTo.class);
    }

    private List<MealTo> createAll(List<MealTo> meals) {
        meals.forEach(ValidationUtil::checkNew);
        log.info("Create meals from list {}", meals);
        List<Meal> mealsList = modelMapper.map(meals, new TypeToken<List<Meal>>() {}.getType());
        return modelMapper.map(service.create(mealsList), new TypeToken<List<MealTo>>() {}.getType());
    }

    private List<MealTo> getAll(int restaurantId) {
        log.info("Get all meals of restaurant {}", restaurantId);
        return modelMapper.map(service.getAll(restaurantId), new TypeToken<List<MealTo>>() {}.getType());
    }

    private List<MealTo> getAllByDate(int restaurantId, @DateTimeFormat(iso = DATE) LocalDate date) {
        log.info("Get all meals of restaurant {} by date {}", restaurantId, date);
        return modelMapper.map(service.getAllByDate(restaurantId, date), new TypeToken<List<MealTo>>() {}.getType());
    }






}
