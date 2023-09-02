package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.AppointmentDao;
import com.project.bankcompany.daoimpl.repository.AppointmentRepository;
import com.project.bankcompany.entity.Appointment;
import com.project.bankcompany.entity.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("AppointmentDaoSpringDataJPAImpl")
public class AppointmentDaoSpringDataJPAImpl implements AppointmentDao {

    private Logger logger = LoggerFactory.getLogger(UserDaoSpringDataJPAImpl.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        Appointment savedappointment = appointmentRepository.save(appointment);
        return savedappointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        Appointment updatedappointment = appointmentRepository.save(appointment);
        return updatedappointment;
    }

    @Override
    public boolean delete(Appointment appointment) {
        boolean deleteResult = false;
        try {
            appointmentRepository.delete(appointment);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete Appointment failed, input Appointment={}, error={}", appointment, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean successFlag = false;
        try{
            appointmentRepository.deleteById(id);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with AppointmentId={}, error={}",id, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with AppointmentId={}, error={}",id, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public List<Appointment> findAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentList;
    }

    @Override
    public Appointment findById(Long id) {
        Appointment appointment = null;
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if(appointmentOptional.isPresent())
            appointment = appointmentOptional.get();
        return appointment;
    }
}
