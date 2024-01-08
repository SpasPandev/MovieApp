package com.spaspandev.movieapp.service;

import com.spaspandev.movieapp.dto.UserDto;
import com.spaspandev.movieapp.enumeration.Role;
import com.spaspandev.movieapp.model.entity.User;
import com.spaspandev.movieapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto changeUserRole(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id: " + id + "was not found!"));

        if (user.getRole().name().equals("USER")) {
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        } else {
            user.setRole(Role.USER);
            userRepository.save(user);
        }

        return modelMapper.map(user, UserDto.class);
    }

    public ResponseEntity<?> deleteUser(Long id) {

        Optional<User> userOpt = userRepository.findById(id);

        if(userOpt.isEmpty() || userOpt.get().isDeleted()){

            return ResponseEntity.notFound().build();
        }

        userOpt.get().setDeleted(true);
        userRepository.save(userOpt.get());

        return ResponseEntity.ok().build();
    }
}
