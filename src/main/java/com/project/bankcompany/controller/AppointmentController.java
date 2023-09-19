package com.project.bankcompany.controller;

import com.project.bankcompany.dto.AppointmentDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.AppointmentService;
import com.project.bankcompany.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto, @RequestParam("username") String username) {
        try {

            UserDto userDto = userService.findByUsername(username);

            // Set the user in the appointment DTO
//            appointmentDto.setUserDto(userDto);

            // Create the appointment
            AppointmentDto savedAppointmentDto = appointmentService.createAppointment(appointmentDto, username);

            // Return a successful response with the saved DTO
            return ResponseEntity.ok(savedAppointmentDto);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            logger.error("Error creating appointment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/allAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppointmentDto> findAllAppointment(){
        List<AppointmentDto> appointmentDtoList = appointmentService.findAll();
        return appointmentDtoList;
    }

    @GetMapping(value = "/allAppointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto findAllAppointment(@PathVariable("id") Long id){
        AppointmentDto appointmentDto = appointmentService.findAppointment(id);
        return appointmentDto;
    }
}

