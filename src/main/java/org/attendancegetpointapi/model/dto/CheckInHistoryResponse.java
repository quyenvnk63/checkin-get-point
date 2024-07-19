package org.attendancegetpointapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckInHistoryResponse {
    private Long id;
    private long userId;
    private int amount;
    private Instant createdAt;
    private int balance;

    public CheckInHistoryResponse(Long id, Long userId, Integer amount, Instant createdAt, Integer balance) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.balance = balance;
    }
}
