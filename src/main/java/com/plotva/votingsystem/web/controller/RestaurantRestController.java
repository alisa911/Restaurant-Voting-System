package com.plotva.votingsystem.web.controller;

import com.plotva.votingsystem.model.Restaurant;
import com.plotva.votingsystem.service.RestaurantService;
import com.plotva.votingsystem.to.RestaurantTo;
import com.plotva.votingsystem.web.View;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.plotva.votingsystem.util.ValidationUtil.assureIdConsistent;
import static com.plotva.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    public static final String REST_URL = "/restaurant";
    private final static Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService service;

    private final ModelMapper modelMapper;

    public RestaurantRestController(RestaurantService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant) {
        RestaurantTo created = create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private RestaurantTo create(RestaurantTo restaurant) {
        checkNew(restaurant);
        log.info("Create {}", restaurant);
        Restaurant created = service.create(modelMapper.map(restaurant, Restaurant.class));
        return modelMapper.map(created, RestaurantTo.class);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantTo restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        log.info("Update {}", restaurant);
        service.update(modelMapper.map(restaurant, Restaurant.class));
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        log.info("Get restaurant with id {}", id);
        return modelMapper.map(service.get(id), RestaurantTo.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Delete restaurant {}", id);
        service.delete(id);
    }

    @GetMapping("/all")
    public List<RestaurantTo> getAll() {
        log.info("Get all restaurants");
        return modelMapper.map(service.getAll(), new TypeToken<List<RestaurantTo>>() {
        }.getType());
    }


}
