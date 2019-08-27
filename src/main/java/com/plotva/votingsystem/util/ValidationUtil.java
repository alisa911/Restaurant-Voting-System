package com.plotva.votingsystem.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean isFound, int id) {
        checkNotFound(isFound, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String message) {
        checkNotFound(object != null, message);
        return object;
    }

    public static void checkNotFound(boolean isFound, String message)  {
        if (!isFound) {
            throw new NotFoundException("Not found entity with " + message);
        }
    }

    public static void checkForSameDate(LocalDate actualDate, LocalDate expectedDate, String message) {
        if (!actualDate.equals(expectedDate))
            throw new DateTimeException(message);
    }

    public static void checkTimeException() {
        LocalTime time = LocalTime.now();
        LocalTime endTime = LocalTime.of(23, 0);
        if (time.isAfter(endTime))
            throw new TimeOverException();
    }

}