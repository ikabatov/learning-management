package ru.ik.learningmanagement.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ik.learningmanagement.service.ScheduleService;

@Component
@RequiredArgsConstructor
public class ScheduleCleanupJob {

    private final ScheduleService scheduleService;

    @Scheduled(cron = "${app.schedule-cleanup.cron}")
    public void cleanUpOldSchedules() {
        scheduleService.deleteOldSchedules();
    }
}
