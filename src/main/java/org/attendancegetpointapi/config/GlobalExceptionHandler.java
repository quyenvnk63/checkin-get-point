package org.attendancegetpointapi.config;

import org.attendancegetpointapi.exception.ResourceNotFoundException;
import org.attendancegetpointapi.model.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> notFoundHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponseDto.error(e.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.error(e.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<?> illegalArgumentException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.error(e.getMessage()));
    }
}
