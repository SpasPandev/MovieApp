package com.spaspandev.movieapp.controller;

import com.spaspandev.movieapp.dto.UserDto;
import com.spaspandev.movieapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/admin/changeUserRole/{id}")
    public ResponseEntity<UserDto> changeUserRole(@PathVariable Long id){

        return ResponseEntity.ok(userService.changeUserRole(id));
    }

    @PatchMapping("/admin/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        return userService.deleteUser(id);
    }
}
