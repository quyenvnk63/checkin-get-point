package org.attendancegetpointapi.service.impl;


import org.attendancegetpointapi.exception.ResourceNotFoundException;
import org.attendancegetpointapi.model.dto.CheckInHistoryResponse;
import org.attendancegetpointapi.model.dto.CheckInResponse;
import org.attendancegetpointapi.model.dto.CheckInStateResponse;
import org.attendancegetpointapi.model.entity.PointTransaction;
import org.attendancegetpointapi.model.entity.User;
import org.attendancegetpointapi.repository.PointTransactionRepository;
import org.attendancegetpointapi.repository.UserRepository;
import org.attendancegetpointapi.service.PointTransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.attendancegetpointapi.constant.AmountConstant.AMOUNT_POINT;
import static org.attendancegetpointapi.constant.AttendanceTimeConstant.*;

@Service
public class PointTransactionImpl implements PointTransactionService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PointTransactionRepository pointTransactionRepository;

    public PointTransactionImpl(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate, PointTransactionRepository pointTransactionRepository) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    @Override
    public CheckInStateResponse getCheckinState(Long userId) {
        if(userId == null) throw new IllegalArgumentException("User id is required" );
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ZoneId userZoneId = ZoneId.of(user.getTimezone());
        ZonedDateTime now = ZonedDateTime.now(userZoneId);

        boolean canCheckIn = false;

        if (isCheckInTimeValid(now)) {
            String slotKey = getCheckinSlotKey(userId, now);
            String hasCheckedInStr = (String) redisTemplate.opsForValue().get(slotKey);
            boolean hasCheckedIn = Boolean.parseBoolean(hasCheckedInStr);
            if (!hasCheckedIn) {
                canCheckIn = true;
            }
        }

        return new CheckInStateResponse(canCheckIn);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class, Throwable.class})
    public CheckInResponse checkin(Long userId) {
        if(userId == null) throw new IllegalArgumentException("User id is required" );
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ZoneId userZoneId = ZoneId.of(user.getTimezone());
        ZonedDateTime now = ZonedDateTime.now(userZoneId);

        if (!isCheckInTimeValid(now)) {
            throw new IllegalStateException("Check-in is not allowed at this time");
        }

        String slotKey = getCheckinSlotKey(userId, now);
        String hasCheckedInStr = (String) redisTemplate.opsForValue().get(slotKey);
        boolean hasCheckedIn = Boolean.parseBoolean(hasCheckedInStr);

        if (hasCheckedIn) {
            throw new IllegalArgumentException("Already checked in for this slot");
        }
        int newPoint = user.getPoint()+ AMOUNT_POINT;

        user.setPoint(newPoint);
        userRepository.save(user);

        PointTransaction pointTransaction = new PointTransaction();
        pointTransaction.setAmount(AMOUNT_POINT);
        pointTransaction.setBalance(newPoint);
        pointTransaction.setUserId(user);
        pointTransactionRepository.save(pointTransaction);

        redisTemplate.opsForValue().set(slotKey, true);
        redisTemplate.expire(slotKey, Duration.between(now, now.toLocalDate().plusDays(1).atStartOfDay(userZoneId)));

        return new CheckInResponse(user.id,newPoint);
    }

    @Override
    public Page<CheckInHistoryResponse> getHistoryCheckin(Long userId, Pageable pageable) {
        if(userId == null) throw new IllegalArgumentException("User id is required" );
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return pointTransactionRepository.getByUserId(user.getId(), pageable);
    }

    private boolean isCheckInTimeValid(ZonedDateTime now) {
        LocalTime time = now.toLocalTime();
        return (time.isAfter(MORNING_CHECKIN_START) && time.isBefore(MORNING_CHECKIN_END)) ||
                (time.isAfter(AFTERNOON_CHECKIN_START) && time.isBefore(AFTERNOON_CHECKIN_END)) ||
                (time.isAfter(EVENING_CHECKIN_START) && time.isBefore(EVENING_CHECKIN_END));
    }

    private String getCheckinSlotKey(Long userId, ZonedDateTime now) {
        String slot;
        LocalTime time = now.toLocalTime();
        if (time.isAfter(MORNING_CHECKIN_START) && time.isBefore(MORNING_CHECKIN_END)) {
            slot = "morning";
        } else if (time.isAfter(AFTERNOON_CHECKIN_START) && time.isBefore(AFTERNOON_CHECKIN_END)) {
            slot = "afternoon";
        } else {
            slot = "evening";
        }
        return "checkin:" + userId + ":" + now.toLocalDate() + ":" + slot;
    }

}
