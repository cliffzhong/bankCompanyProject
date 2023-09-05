package com.project.bankcompany.service.impl;

import com.project.bankcompany.dao.AppointmentDao;
import com.project.bankcompany.dto.AppointmentDto;
import com.project.bankcompany.dto.RoleDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.Appointment;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.AppointmentService;
import com.project.bankcompany.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private Logger logger= LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = DtoAndEntityConvertUtil.convertAppointmentDtoToAppointment(appointmentDto);
        Appointment savedAppointment = appointmentDao.save(appointment);
        AppointmentDto savedAppointmentDto = DtoAndEntityConvertUtil.convertAppointmentToAppointmentDto(savedAppointment);
        return savedAppointmentDto;
    }

    @Override
    public List<AppointmentDto> findAll() {
        List<Appointment> appointmentList = appointmentDao.findAllAppointments();
        List<AppointmentDto> appointmentDtoList = getAppointmentDtoListByAppointmentList(appointmentList);
        displayAppointmentDtoList(appointmentDtoList);
        return appointmentDtoList;
    }

    private List<AppointmentDto> getAppointmentDtoListByAppointmentList(List<Appointment> appointmentList) {
        List<AppointmentDto> appointmentDtoList = new ArrayList<>();
        for(Appointment appointment : appointmentList) {
//            UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
            AppointmentDto appointmentDto = DtoAndEntityConvertUtil.convertAppointmentToAppointmentDto(appointment);
            appointmentDtoList.add(appointmentDto);
        }
        return appointmentDtoList;
    }

    private void displayAppointmentDtoList(List<AppointmentDto> appointmentDtoList) {
        int index = 1;
        for(AppointmentDto appointmentDto : appointmentDtoList) {
            displayAppointmentDto(index++, appointmentDto);
        }
    }

    private void displayAppointmentDto(int i, AppointmentDto appointment) {
        logger.info("======= UserDto No.{} ========", i);
        logger.info("\t appointment.id={}", appointment.getId());
        logger.info("\t appointment.date={}", appointment.getDate());
        logger.info("\t appointment.location={}", appointment.getLocation());
        logger.info("\t appointment.description={}", appointment.getDescription());
        logger.info("\t appointment.confirmed={}", appointment.getConfirmed());
        logger.info("\t appointment.userid={}", appointment.getUser());
    }

    @Override
    public AppointmentDto findAppointment(Long id) {
        return null;
    }

    @Override
    public boolean confirmAppointment(Long id) {
        return false;
    }
}
