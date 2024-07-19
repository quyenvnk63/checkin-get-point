package org.attendancegetpointapi.repository;

import org.attendancegetpointapi.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User,Long> {
}
