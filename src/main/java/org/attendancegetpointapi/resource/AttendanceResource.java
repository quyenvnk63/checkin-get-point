package org.attendancegetpointapi.resource;

import org.attendancegetpointapi.model.dto.BaseResponseDto;
import org.attendancegetpointapi.model.dto.CheckInHistoryResponse;
import org.attendancegetpointapi.model.entity.PointTransaction;
import org.attendancegetpointapi.service.PointTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/checkin")
public class AttendanceResource {

    private final PointTransactionService pointTransactionService;


    public AttendanceResource(PointTransactionService pointTransactionService) {
        this.pointTransactionService = pointTransactionService;
    }

    // api get checkin state by user id
    @GetMapping("/{userId}/status")
    public ResponseEntity<BaseResponseDto> getCheckinState(@PathVariable Long userId) {
        return ResponseEntity.ok(BaseResponseDto.success(pointTransactionService.getCheckinState(userId)));
    }

    // api checkin

    @PostMapping("/{userId}")
    public ResponseEntity<BaseResponseDto> checkIn(@PathVariable Long userId) {
        return ResponseEntity.ok(BaseResponseDto.success(pointTransactionService.checkin(userId)));
    }

    // api get history checkin by user id
    @GetMapping("/{userId}/history")
    public ResponseEntity<BaseResponseDto> getHistoryCheckin(@PathVariable Long userId, @PageableDefault(page = 0,size = 10) Pageable pageable ) {
        Page<CheckInHistoryResponse> pointTransactionPage = pointTransactionService.getHistoryCheckin(userId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("content", pointTransactionPage.getContent());
        response.put("totalPages", pointTransactionPage.getTotalPages());
        response.put("totalItems", pointTransactionPage.getTotalElements());
        response.put("currentPage", pointTransactionPage.getNumber());
        BaseResponseDto baseResponseDto = BaseResponseDto.success(response);
        return ResponseEntity.ok(baseResponseDto);
    }
}
