package com.uzima.services;

import com.uzima.enums.AppointmentStatus;
import com.uzima.models.Appointment;
import com.uzima.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

@Transactional
public class NotificationService {

    private final EmailService emailService;
    private final AppointmentRepository appointmentRepository;

    public void processNotifications() {

        log.info("Notification check running at {}", LocalDateTime.now());

        List<Appointment> appointments = appointmentRepository.findByStatusInAndReminderSentAtIsNull(
                List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED));

        appointments.forEach(appointment -> {

                    LocalDateTime appointmentTime = appointment.getAppointmentDateTime();
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime reminderWindowEnd = now.plusHours(24);

                    if (appointmentTime.isAfter(now)
                            && !appointmentTime.isAfter(reminderWindowEnd)) {

                        log.info(
                                "Appointment {} is eligible for reminder",
                                appointment.getId()
                        );

                        emailService.sendAppointmentReminder(appointment);

                        appointment.setReminderSentAt(LocalDateTime.now());

                        appointmentRepository.save(appointment);
                    }
                }

        );
    }


}
