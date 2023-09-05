package com.project.bankcompany.controller;

import com.project.bankcompany.dto.AppointmentDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.service.AppointmentService;
import com.project.bankcompany.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private Logger logger= LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointmentDto, UserDto userDto) {
        appointmentDto.setConfirmed(false);
        appointmentDto.setUser(userDto.convertUserDtoToUser());
        AppointmentDto savedAppointmentDto = appointmentService.createAppointment(appointmentDto);
        return savedAppointmentDto;
    }



}
