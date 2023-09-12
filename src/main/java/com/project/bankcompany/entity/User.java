/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.project.bankcompany.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.bankcompany.dto.RoleDto;
import com.project.bankcompany.dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    public User() { }
    public User(String firstName, String lastName, String email, String username,
                String password, String secretKey, String phone, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.secretKey = secretKey;
        this.phone = phone;
        this.enabled = enabled;
    }


    @Id
    //@SequenceGenerator(name = "user_id_generator", sequenceName = "user_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "user_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "phone")
    private String phone;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne
    private CheckingAccount checkingAccount;

    @OneToOne
    private SavingsAccount savingsAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipient> recipientList;

    //@ManyToMany(mappedBy = "users", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
//    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles;

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

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public List<Recipient> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }

    public Set<Role> getRoles() {
        if(roles == null)
            roles = new HashSet<Role>();
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }

    public UserDto convertUserToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(getId());
        userDto.setUsername(getUsername());
        userDto.setPassword(getPassword());
        userDto.setSecretKey(getSecretKey());
        userDto.setFirstName(getFirstName());
        userDto.setLastName(getLastName());
        userDto.setEmail(getEmail());
        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(getRoles()));
        return userDto;
    }

    private Set<RoleDto> getRoleDtoSetByRolesWithoutUserDto(Set<Role> roles) {
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for(Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDtoWithoutUser(role);
            roleDtoSet.add(roleDto);
        }
        return roleDtoSet;
    }

    private static RoleDto convertRoleToRoleDtoWithoutUser(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setAllowedResource(role.getAllowedResource());
        roleDto.setAllowedRead(role.isAllowedRead());
        roleDto.setAllowedCreate(role.isAllowedCreate());
        roleDto.setAllowedUpdate(role.isAllowedUpdate());
        roleDto.setAllowedDelete(role.isAllowedDelete());
//        roleDto.setUserDtoSet(getUserDtoSetByUsers(role.getUsers()));
        return roleDto;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,firstName,lastName,email,username,password,secretKey,phone,enabled);
    }

//    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
