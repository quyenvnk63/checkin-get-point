package org.attendancegetpointapi.repository;

import org.attendancegetpointapi.model.dto.CheckInHistoryResponse;
import org.attendancegetpointapi.model.entity.PointTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PointTransactionRepository extends BaseRepository<PointTransaction,Long> {

    @Query( " SELECT new org.attendancegetpointapi.model.dto.CheckInHistoryResponse(pt.id,pt.userId.id,pt.amount,pt.createdDate,pt.balance) " +
            " FROM PointTransaction pt " +
            " WHERE pt.userId.id= :userId " +
            " ORDER BY pt.createdDate DESC ")
    Page<CheckInHistoryResponse> getByUserId(@Param("userId") Long userId, Pageable pageable);

}
