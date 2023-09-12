package com.project.bankcompany.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bankcompany.entity.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDto {
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private String secretKey;

    private String phone;

    private boolean enabled;

    private CheckingAccountDto checkingAccountDto;

    private SavingsAccountDto savingsAccountDto;

    @JsonIgnore
    private List<AppointmentDto> appointmentDtoList;

    @JsonIgnore
    private List<RecipientDto> recipientDtoList;

    @JsonIgnore
    private Set<RoleDto> roleDtoSet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleDto> getRoleDtoSet() {
        return roleDtoSet;
    }

    public void setRoleDtoSet(Set<RoleDto> roleDtoSet) {
        this.roleDtoSet = roleDtoSet;
    }

    public CheckingAccountDto getCheckingAccountDto() {
        return checkingAccountDto;
    }

    public void setCheckingAccountDto(CheckingAccountDto checkingAccountDto) {
        this.checkingAccountDto = checkingAccountDto;
    }

    public SavingsAccountDto getSavingsAccountDto() {
        return savingsAccountDto;
    }

    public void setSavingsAccountDto(SavingsAccountDto savingsAccountDto) {
        this.savingsAccountDto = savingsAccountDto;
    }

    public List<AppointmentDto> getAppointmentDtoList() {
        return appointmentDtoList;
    }

    public void setAppointmentDtoList(List<AppointmentDto> appointmentDtoList) {
        this.appointmentDtoList = appointmentDtoList;
    }

    public List<RecipientDto> getRecipientDtoList() {
        return recipientDtoList;
    }

    public void setRecipientDtoList(List<RecipientDto> recipientDtoList) {
        this.recipientDtoList = recipientDtoList;
    }

    public User convertUserDtoToUser() {
        User user = new User();
        user.setId(getId());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setEmail(getEmail());
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setSecretKey(getSecretKey());
        user.setPhone(getPhone());
        user.setEnabled(getEnabled());
        user.setCheckingAccount(getCheckingAccountDto().convertCheckingAccountDtoToCheckingAccount());
        user.setSavingsAccount(getSavingsAccountDto().convertSavingsAccountDtoToSavingsAccount());
        user.setRecipientList(getRecipientsByRecipientDtoList(getRecipientDtoList()));
        user.setAppointmentList(getAppointmentsByAppointmentDtoList(getAppointmentDtoList()));
        user.setRoles(getRolesByRoleDtoSet(getRoleDtoSet()));
        return user;


    }

    private List<Appointment> getAppointmentsByAppointmentDtoList(List<AppointmentDto> appointmentDtoList) {
        List<Appointment> appointmentList = new ArrayList<>();
        for(AppointmentDto appointmentDto : appointmentDtoList) {
            Appointment appointment = convertAppointmentDtoToAppointment(appointmentDto);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    private List<Recipient> getRecipientsByRecipientDtoList(List<RecipientDto> recipientDtoList) {
        List<Recipient> recipientList = new ArrayList<>();
        for(RecipientDto recipientDto : recipientDtoList) {
            Recipient recipient = convertRecipientDtoToRecipient(recipientDto);
            recipientList.add(recipient);
        }
        return recipientList;
    }

    private Set<Role> getRolesByRoleDtoSet(Set<RoleDto> roleDtoSet) {
        Set<Role> roleSet = new HashSet<>();
        for(RoleDto roleDto : roleDtoSet) {
            Role role = convertRoleDtoToRoleWithoutUser(roleDto);
            roleSet.add(role);
        }
        return roleSet;
    }

    private Role convertRoleDtoToRoleWithoutUser(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setAllowedResource(roleDto.getAllowedResource());
        role.setAllowedRead(roleDto.isAllowedRead());
        role.setAllowedCreate(roleDto.isAllowedCreate());
        role.setAllowedUpdate(roleDto.isAllowedUpdate());
        role.setAllowedDelete(roleDto.isAllowedDelete());
//        role.setUsers(getusersByUserDtoSet(roleDto.getUserDtoSet()));
        return role;
    }

    private Appointment convertAppointmentDtoToAppointment(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDate(appointmentDto.getDate());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setConfirmed(appointmentDto.getConfirmed());
        appointment.setLocation(appointmentDto.getLocation());
        appointment.setUser(appointmentDto.getUserDto().convertUserDtoToUser());

        return appointment;
    }

    private Recipient convertRecipientDtoToRecipient(RecipientDto recipientDto){
        Recipient recipient = new Recipient();
        recipient.setId(recipientDto.getId());
        recipient.setDescription(recipientDto.getDescription());
        recipient.setAccountNumber(recipientDto.getAccountNumber());
        recipient.setName(recipientDto.getName());
        recipient.setEmail(recipientDto.getEmail());
        recipient.setPhone(recipientDto.getPhone());
        recipient.setUser(recipientDto.getUser());
        return recipient;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                ", roleDtoSet=" + roleDtoSet +
                '}';
    }
}
