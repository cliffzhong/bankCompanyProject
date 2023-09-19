package com.project.bankcompany.service;

import com.project.bankcompany.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto, String username);

    List<AppointmentDto> findAll();

    AppointmentDto findAppointment(Long id);

    boolean confirmAppointment(Long id);
}
