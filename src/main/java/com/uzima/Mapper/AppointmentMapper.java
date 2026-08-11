package com.uzima.Mapper;

import com.uzima.dtos.AppointmentRequest;
import com.uzima.dtos.AppointmentResponse;
import com.uzima.models.Appointment;

public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment appointment) {

        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentDateTime(appointment.getAppointmentDateTime());
        response.setStatus(appointment.getStatus());
        response.setReason(appointment.getReason());
        response.setNotes(appointment.getNotes());
        response.setAppointmentType(appointment.getAppointmentType());

        return response;
    }

    public static Appointment fromRequest(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        updateAppointment(appointment, request);

        return appointment;
    }

    public static void updateAppointment(Appointment appointment, AppointmentRequest request) {

        //Appointment
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setStatus(request.getStatus());
        appointment.setReason(request.getReason());
        appointment.setNotes(request.getNotes());
        appointment.setAppointmentType(request.getAppointmentType());


    }
}
