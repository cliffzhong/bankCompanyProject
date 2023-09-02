package com.project.bankcompany.dao;

import com.project.bankcompany.entity.Appointment;

import java.util.List;

public interface AppointmentDao {

    Appointment save(Appointment appointment);

    Appointment update(Appointment appointment);

    boolean delete(Appointment appointment);

    boolean deleteById(Long id);

    List<Appointment> findAllAppointments();

    Appointment findById(Long id);
}
