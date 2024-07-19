package org.attendancegetpointapi.service;

import org.attendancegetpointapi.model.entity.User;

public interface UserService {
    public User getProfile (Long userId);
}
