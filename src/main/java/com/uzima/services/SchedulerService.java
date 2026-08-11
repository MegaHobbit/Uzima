package com.uzima.services;

import com.uzima.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final NotificationService notificationService;

    @Scheduled(fixedRate = 30_000)
    public void processScheduledNotifications() {
        notificationService.processNotifications();
    }
}
