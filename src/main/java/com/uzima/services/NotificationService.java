package com.uzima.services;

import com.uzima.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final PatientRepository patientRepository;

    public void processNotifications() {

        var patients = patientRepository.findAll();

        for (var patient : patients) {

            if (patient.getEmail() == null || patient.getEmail().isBlank()) {
                continue;
            }

            emailService.sendEmail(
                    patient.getEmail(),
                    "Uzima Notification",
                    "Hello " + patient.getFirstName()
                            + ", this is a notification from Uzima."
            );
        }
    }

}
