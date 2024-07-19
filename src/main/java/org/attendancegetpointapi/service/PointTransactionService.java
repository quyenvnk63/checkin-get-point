package org.attendancegetpointapi.service;

import org.attendancegetpointapi.model.dto.CheckInHistoryResponse;
import org.attendancegetpointapi.model.dto.CheckInResponse;
import org.attendancegetpointapi.model.dto.CheckInStateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointTransactionService {

    CheckInStateResponse getCheckinState(Long userId);

    CheckInResponse checkin(Long userId);

    Page<CheckInHistoryResponse> getHistoryCheckin(Long userId, Pageable pageable);

}
