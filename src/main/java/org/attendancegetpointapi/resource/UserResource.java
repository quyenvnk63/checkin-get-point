package org.attendancegetpointapi.resource;

import org.attendancegetpointapi.model.dto.BaseResponseDto;
import org.attendancegetpointapi.model.entity.User;
import org.attendancegetpointapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    private final UserService userService;;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    // get point by user id
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponseDto> getProfile(@PathVariable Long userId) {
        User profile = userService.getProfile(userId);
        BaseResponseDto responseDto = BaseResponseDto.success(profile);
        return ResponseEntity.ok(responseDto);
    }


}
