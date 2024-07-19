package org.attendancegetpointapi.service.impl;

import org.attendancegetpointapi.exception.ResourceNotFoundException;
import org.attendancegetpointapi.model.entity.User;
import org.attendancegetpointapi.repository.UserRepository;
import org.attendancegetpointapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getProfile(Long userId) {
        if(userId == null) throw new IllegalArgumentException("User id is required" );
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
