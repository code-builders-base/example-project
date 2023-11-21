package com.pifrans.exampleproject.rest.controllers;


import com.pifrans.exampleproject.domains.dtos.UserDto;
import com.pifrans.exampleproject.domains.entities.User;
import com.pifrans.exampleproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;


    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Resume> findById(@PathVariable Long id) {
        var entity = userService.findById(id);
        var dto = mapper.map(entity, UserDto.Resume.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto.Resume>> findAll() {
        var entities = userService.findAll();
        var dtos = entities.stream().map(e -> mapper.map(e, UserDto.Resume.class)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserDto.Resume>> findByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linesPerPage, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction) {
        var entityPages = userService.findByPage(page, linesPerPage, orderBy, direction);
        var entities = entityPages.getContent();
        var dtos = entities.stream().map(e -> mapper.map(e, UserDto.Resume.class)).toList();
        var dtoPages = new PageImpl<>(dtos, entityPages.getPageable(), entityPages.getTotalElements());

        return new ResponseEntity<>(dtoPages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto.Resume> save(@Validated @RequestBody UserDto.Save body) {
        var entity = mapper.map(body, User.class);
        var savedEntity = userService.save(entity);
        var dto = mapper.map(savedEntity, UserDto.Resume.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<UserDto.Resume>> saveAll(@Validated @RequestBody List<UserDto.Save> body) {
        var entities = body.stream().map(d -> mapper.map(d, User.class)).toList();
        var savedEntities = userService.saveAll(entities);
        var dtos = savedEntities.stream().map(e -> mapper.map(e, UserDto.Resume.class)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto.Resume> update(@Validated @RequestBody UserDto.Update body) {
        var entity = mapper.map(body, User.class);
        var updatedEntity = userService.update(entity, entity.getId());
        var dto = mapper.map(updatedEntity, UserDto.Resume.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto.Full> deleteById(@PathVariable Long id) {
        var entity = userService.deleteById(id);
        var dto = mapper.map(entity, UserDto.Full.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
