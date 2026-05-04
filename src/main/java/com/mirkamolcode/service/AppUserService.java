package com.mirkamolcode.service;

import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.AppUser;
import com.mirkamolcode.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.mirkamolcode.model.enums.ResponseMessage.UNKNOWN_USER;
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }

    public AppUser getUserById(UUID userId) {
       return appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(UNKNOWN_USER.getMessage()));

    }

}
