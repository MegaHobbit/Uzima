package com.uzima.services;

import com.uzima.models.Appointment;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendAppointmentReminder(Appointment appointment) {

        String recipient = appointment.getPatient().getEmail();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(recipient);
        message.setSubject("Uzima Appointment Reminder");
        message.setText(
                "Dear " + appointment.getPatient().getFirstName() + ",\n\n" +
                        "This is a reminder that you have an appointment scheduled for " +
                        appointment.getAppointmentDateTime() + ".\n\n" +
                        "Reason: " + appointment.getReason() + "\n" +
                        "Appointment Type: " + appointment.getAppointmentType() + "\n\n" +
                        "Thank you,\n" +
                        "Uzima"
        );

        mailSender.send(message);

        log.info(
                "Appointment reminder sent for appointment ID: {}",
                appointment.getId()
        );
    }

}
