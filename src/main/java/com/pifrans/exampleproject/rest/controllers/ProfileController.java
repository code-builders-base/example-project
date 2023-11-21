package com.pifrans.exampleproject.rest.controllers;


import com.pifrans.exampleproject.domains.dtos.ProfileDto;
import com.pifrans.exampleproject.domains.entities.Profile;
import com.pifrans.exampleproject.services.ProfileService;
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
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final ModelMapper mapper;


    public ProfileController(ProfileService profileService, ModelMapper mapper) {
        this.profileService = profileService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto.Full> findById(@PathVariable Long id) {
        var entity = profileService.findById(id);
        var dto = mapper.map(entity, ProfileDto.Full.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProfileDto.Full>> findAll() {
        var entities = profileService.findAll();
        var dtos = entities.stream().map(e -> mapper.map(e, ProfileDto.Full.class)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProfileDto.Full>> findByPage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linesPerPage, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "ASC") String direction) {
        var entityPages = profileService.findByPage(page, linesPerPage, orderBy, direction);
        var entities = entityPages.getContent();
        var dtos = entities.stream().map(e -> mapper.map(e, ProfileDto.Full.class)).toList();
        var dtoPages = new PageImpl<>(dtos, entityPages.getPageable(), entityPages.getTotalElements());
        return ResponseEntity.ok().body(dtoPages);
    }

    @PostMapping
    public ResponseEntity<ProfileDto.Full> save(@Validated @RequestBody ProfileDto.Save body) {
        var entity = mapper.map(body, Profile.class);
        var savedEntity = profileService.save(entity);
        var dto = mapper.map(savedEntity, ProfileDto.Full.class);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<ProfileDto.Full>> saveAll(@Validated @RequestBody List<ProfileDto.Full> body) {
        var entities = body.stream().map(d -> mapper.map(d, Profile.class)).toList();
        var savedEntities = profileService.saveAll(entities);
        var dtos = savedEntities.stream().map(e -> mapper.map(e, ProfileDto.Full.class)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProfileDto.Full> update(@Validated @RequestBody ProfileDto.Full body) {
        var entity = mapper.map(body, Profile.class);
        var updatedEntity = profileService.update(entity, entity.getId());
        var dto = mapper.map(updatedEntity, ProfileDto.Full.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDto.Full> deleteById(@PathVariable Long id) {
        var entity = profileService.deleteById(id);
        var dto = mapper.map(entity, ProfileDto.Full.class);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
