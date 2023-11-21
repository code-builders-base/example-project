package com.pifrans.exampleproject.services;


import com.pifrans.exampleproject.domains.entities.Profile;
import com.pifrans.exampleproject.errors.exceptions.NotFoundException;
import com.pifrans.exampleproject.repositories.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;


    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile findById(Long id) {
        String message = String.format("No profile ID (%d) found!", id);
        return profileRepository.findById(id).orElseThrow(() -> new NotFoundException(message));
    }

    public Profile save(Profile object) {
        return profileRepository.save(object);
    }

    public List<Profile> saveAll(List<Profile> list) {
        return profileRepository.saveAll(list);
    }

    public Profile update(Profile object, Long id) {
        if (profileRepository.existsById(id)) {
            return profileRepository.save(object);
        }
        String message = String.format("Unable to update profile ID (%d), not found!", id);
        throw new NotFoundException(message);
    }

    public Profile deleteById(Long id) {
        String message = String.format("Could not delete the profile, as there is no user with ID (%d)!", id);
        Profile object = profileRepository.findById(id).orElseThrow(() -> new NotFoundException(message));

        profileRepository.deleteById(id);
        return object;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Page<Profile> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return profileRepository.findAll(pageRequest);
    }
}
