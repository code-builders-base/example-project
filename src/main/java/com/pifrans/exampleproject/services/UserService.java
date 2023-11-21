package com.pifrans.exampleproject.services;


import com.pifrans.exampleproject.domains.entities.User;
import com.pifrans.exampleproject.errors.exceptions.NotFoundException;
import com.pifrans.exampleproject.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        String message = String.format("No user ID (%d) found!", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(message));
    }

    public User save(User object) {
        return userRepository.save(object);
    }

    public List<User> saveAll(List<User> list) {
        return userRepository.saveAll(list);
    }

    public User update(User object, Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.save(object);
        }
        String message = String.format("Unable to update user ID (%d), not found!", id);
        throw new NotFoundException(message);
    }

    public User deleteById(Long id) {
        String message = String.format("Could not delete the user, as there is no user with ID (%d)!", id);
        User object = userRepository.findById(id).orElseThrow(() -> new NotFoundException(message));

        userRepository.deleteById(id);
        return object;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return userRepository.findAll(pageRequest);
    }
}
