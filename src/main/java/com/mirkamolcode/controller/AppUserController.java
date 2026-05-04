package com.mirkamolcode.controller;

import com.mirkamolcode.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;


    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAppUsers(){
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppUser(@PathVariable UUID id){
        return ResponseEntity.ok(appUserService.getUserById(id));
    }
}
