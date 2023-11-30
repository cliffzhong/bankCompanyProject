package com.project.bankcompany.daoimpl.springdatajpa;

import com.project.bankcompany.daoimpl.repository.AppointmentRepository;
import com.project.bankcompany.daoimpl.sproingdatajpa.AppointmentDaoSpringDataJPAImpl;
import com.project.bankcompany.entity.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AppointmentDaoSpringDataJPAImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @InjectMocks
    private AppointmentDaoSpringDataJPAImpl appointmentDao;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAppointment() {
        Appointment mockedAppointment = mock(Appointment.class);

        when(appointmentRepository.save(mockedAppointment)).thenReturn(mockedAppointment);

        Appointment savedAppointment = appointmentDao.save(mockedAppointment);

        verify(appointmentRepository, times(1)).save(mockedAppointment);

        assertNotNull(savedAppointment);
    }

    @Test
    public void testUpdateAppointment(){
        Appointment mockedAppointment = mock(Appointment.class);

        when(appointmentRepository.save(mockedAppointment)).thenReturn(mockedAppointment);

        Appointment updatedAppointment = appointmentDao.update(mockedAppointment);

        verify(appointmentRepository, times(1)).save(mockedAppointment);

        assertNotNull(updatedAppointment);
    }

    @Test
    public void testDeleteAppointment(){
        Appointment mockedAppointment = mock(Appointment.class);

        doNothing().when(appointmentRepository).delete(mockedAppointment);

        boolean deleteResult = appointmentDao.delete(mockedAppointment);

        verify(appointmentRepository, times(1)).delete(mockedAppointment);

        assertTrue(deleteResult);
    }

    @Test
    public void testDeleteById(){
        Long appointmentId = 1L;

        doNothing().when(appointmentRepository).deleteById(appointmentId);

        boolean successFlag = appointmentDao.deleteById(appointmentId);

        verify(appointmentRepository, times(1)).deleteById(appointmentId);

        assertTrue(successFlag);
    }

    @Test
    public void testFindAllAppointments(){
        List<Appointment> mockedAppointmentList = new ArrayList<>();

        // Create spy objects for appointments
        Appointment appointmentOne = spy(new Appointment());
        Appointment appointmentTwo = spy(new Appointment());
        Appointment appointmentThree = spy(new Appointment());

        mockedAppointmentList.add(appointmentOne);
        mockedAppointmentList.add(appointmentTwo);
        mockedAppointmentList.add(appointmentThree);

        // Define the behavior of the appointmentRepository.findAll method
        when(appointmentRepository.findAll()).thenReturn(mockedAppointmentList);

        // Perform the findAllAppointments operation
        List<Appointment> appointmentList = appointmentDao.findAllAppointments();

        // Verify that the findAll method was called
        verify(appointmentRepository, times(1)).findAll();

        assertNotNull(appointmentList);
        assertEquals(3, appointmentList.size());
    }

    @Test
    public void testFindById(){
        Appointment mockedAppointment = mock(Appointment.class);

        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(mockedAppointment));

        Appointment appointment = appointmentDao.findById(appointmentId);

        verify(appointmentRepository,times(1)).findById(appointmentId);

        assertNotNull(appointment);
        assertEquals(mockedAppointment, appointment);
    }



}

