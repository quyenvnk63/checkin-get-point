package org.attendancegetpointapi.constant;


import java.time.LocalTime;


public class AttendanceTimeConstant {
    public static final LocalTime MORNING_CHECKIN_START = LocalTime.of(7, 0);
    public static final LocalTime MORNING_CHECKIN_END = LocalTime.of(9, 0);
    public static final LocalTime AFTERNOON_CHECKIN_START = LocalTime.of(13, 0);
    public static final LocalTime AFTERNOON_CHECKIN_END = LocalTime.of(16, 0);
    public static final LocalTime EVENING_CHECKIN_START = LocalTime.of(17, 0);
    public static final LocalTime EVENING_CHECKIN_END = LocalTime.of(19, 0);
}
