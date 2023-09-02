package com.project.bankcompany.dto;

import com.project.bankcompany.entity.Appointment;
import com.project.bankcompany.entity.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class AppointmentDto {

    private Long id;

    private LocalDate date;

    private String location;

    private String description;

    private boolean confirmed;

    private User user;

    private Appointment convertAppointmentDtoToAppointment(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment() ;
        appointment.setId(getId());
        appointment.setDate(getDate());
        appointment.setLocation(getLocation());
        appointment.setDescription(getDescription());
        appointment.setConfirmed(getConfirmed());
        appointment.setUser(getUser());
        return appointment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
