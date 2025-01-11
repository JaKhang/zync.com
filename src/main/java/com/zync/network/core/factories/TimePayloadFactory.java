package com.zync.network.core.factories;

import com.zync.network.core.domain.TimePayload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class TimePayloadFactory {
    public TimePayload create(LocalDateTime createdAtTime) {
        if (createdAtTime == null) {
            throw new IllegalArgumentException("createdAt cannot be null or blank");
        }
        try {
            // Parse the createdAt string into LocalDateTime


            // Get the current time
            LocalDateTime now = LocalDateTime.now();

            // Calculate the time difference in various units
            long seconds = ChronoUnit.SECONDS.between(createdAtTime, now);
            long minutes = ChronoUnit.MINUTES.between(createdAtTime, now);
            long hours = ChronoUnit.HOURS.between(createdAtTime, now);
            long days = ChronoUnit.DAYS.between(createdAtTime, now);
            long weeks = ChronoUnit.WEEKS.between(createdAtTime, now);
            long years = ChronoUnit.YEARS.between(createdAtTime, now);

            // Return the appropriate human-readable format using ChronoUnit
            if (years > 0) {
                return new TimePayload((int) years, ChronoUnit.YEARS);
            } else if (weeks > 0) {
                return new TimePayload((int) weeks, ChronoUnit.WEEKS);
            } else if (days >= 1) {
                return new TimePayload((int) days, ChronoUnit.DAYS);
            } else if (hours >= 1) {
                return new TimePayload((int) hours, ChronoUnit.HOURS);
            } else if (minutes >= 1) {
                return new TimePayload((int) minutes, ChronoUnit.MINUTES);
            } else {
                return new TimePayload((int) seconds, ChronoUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd HH:mm:ss");
        }
    }

    public static void main(String[] args) {
        System.out.println(new TimePayloadFactory().create(LocalDateTime.now().minusMinutes(1)));
        System.out.println(new TimePayloadFactory().create(LocalDateTime.now().minusDays(1)));
        System.out.println(new TimePayloadFactory().create(LocalDateTime.now().minusWeeks(1)));
        System.out.println(new TimePayloadFactory().create(LocalDateTime.now().minusMonths(1)));
    }
}
