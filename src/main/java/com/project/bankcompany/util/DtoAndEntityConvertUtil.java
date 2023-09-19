package com.project.bankcompany.util;

import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DtoAndEntityConvertUtil {

    public static User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setSecretKey(userDto.getSecretKey());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCheckingAccount(convertCheckingAccountDtoToCheckingAccount(userDto.getCheckingAccountDto()));
        user.setSavingsAccount(convertSavingsAccountDtoToSavingsAccount(userDto.getSavingsAccountDto()));
        user.setEnabled(userDto.getEnabled());
        user.setAppointmentList(getAppointmentListByAppointmentDtoList(userDto.getAppointmentDtoList()));
        user.setRecipientList(getRecipientListByRecipientDtoList(userDto.getRecipientDtoList()));
        user.setRoles(getRolesByRoleDtoSet(userDto.getRoleDtoSet()));
        return user;
    }

    private static List<Recipient> getRecipientListByRecipientDtoList(List<RecipientDto> recipientDtoList) {
        List<Recipient> recipientList = new ArrayList<>();
        for(RecipientDto recipientDto : recipientDtoList) {
            Recipient recipient = convertRecipientDtoToRecipientWithoutUser(recipientDto);
            recipientList.add(recipient);
        }
        return recipientList;
    }

    private static List<Appointment> getAppointmentListByAppointmentDtoList(List<AppointmentDto> appointmentDtoList) {
        List<Appointment> appointmentList = new ArrayList<>();
        for(AppointmentDto appointmentDto : appointmentDtoList) {
            Appointment appointment = convertAppointmentDtoToAppointmentWithoutUser(appointmentDto);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    private static Set<Role> getRolesByRoleDtoSet(Set<RoleDto> roleDtoSet) {
        Set<Role> roleSet = new HashSet<>();
        for(RoleDto roleDto : roleDtoSet) {
            Role role = convertRoleDtoToRoleWithoutUser(roleDto);
            roleSet.add(role);
        }
        return roleSet;
    }

    private static Role convertRoleDtoToRoleWithoutUser(RoleDto roleDto) {
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

    public static UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setSecretKey(user.getSecretKey());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCheckingAccountDto(convertCheckingAccountToCheckingAccountDto(user.getCheckingAccount()));
        userDto.setSavingsAccountDto(convertSavingsAccountToSavingsAccountDto(user.getSavingsAccount()));
        userDto.setEnabled(user.getEnabled());
        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(user.getRoles()));
        return userDto;
    }

    private static Set<RoleDto> getRoleDtoSetByRolesWithoutUserDto(Set<Role> roles) {
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

    private static Set<RoleDto> getRoleDtoSetByRoles(Set<Role> roles) {
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for(Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDto(role);
            roleDtoSet.add(roleDto);
        }
        return roleDtoSet;
    }

    public static Role convertRoleDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setAllowedResource(roleDto.getAllowedResource());
        role.setAllowedRead(roleDto.isAllowedRead());
        role.setAllowedCreate(roleDto.isAllowedCreate());
        role.setAllowedUpdate(roleDto.isAllowedUpdate());
        role.setAllowedDelete(roleDto.isAllowedDelete());
        role.setUsers(getusersByUserDtoSet(roleDto.getUserDtoSet()));
        return role;
    }

    private static Set<User> getusersByUserDtoSet(Set<UserDto> userDtoSet) {
        Set<User> userSet = new HashSet<>();
        for(UserDto userDto : userDtoSet) {
            User user = convertUserDtoToUser(userDto);
            userSet.add(user);
        }
        return userSet;
    }

    public static RoleDto convertRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setAllowedResource(role.getAllowedResource());
        roleDto.setAllowedRead(role.isAllowedRead());
        roleDto.setAllowedCreate(role.isAllowedCreate());
        roleDto.setAllowedUpdate(role.isAllowedUpdate());
        roleDto.setAllowedDelete(role.isAllowedDelete());
        roleDto.setUserDtoSet(getUserDtoSetByUsers(role.getUsers()));
        return roleDto;
    }

    private static Set<UserDto> getUserDtoSetByUsers(Set<User> users) {
        Set<UserDto> userDtoSet = new HashSet<>();
        for(User user : users) {
            UserDto userDto = convertUserToUserDtoWithoutRole(user);
            userDtoSet.add(userDto);
        }
        return userDtoSet;
    }

    public static UserDto convertUserToUserDtoWithoutRole(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setSecretKey(user.getSecretKey());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setCheckingAccountDto(convertCheckingAccountToCheckingAccountDto(user.getCheckingAccount()));
        userDto.setSavingsAccountDto(convertSavingsAccountToSavingsAccountDto(user.getSavingsAccount()));
        userDto.setEnabled(user.getEnabled());
//        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(user.getRoles()));
        return userDto;
    }



    public static Appointment convertAppointmentDtoToAppointment(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setLocation(appointmentDto.getLocation());
        appointment.setDate(appointmentDto.getDate());
        appointment.setConfirmed(appointmentDto.getConfirmed());
        appointment.setUser(convertUserDtoToUser(appointmentDto.getUserDto()));
        return appointment;
    }

    public static Appointment convertAppointmentDtoToAppointmentWithoutUser(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setLocation(appointmentDto.getLocation());
        appointment.setDate(appointmentDto.getDate());
        appointment.setConfirmed(appointmentDto.getConfirmed());
//        appointment.setUser(appointmentDto.getUserDto().convertUserDtoToUser());
        return appointment;
    }

    public static AppointmentDto  convertAppointmentToAppointmentDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDescription(appointment.getDescription());
        appointmentDto.setLocation(appointment.getLocation());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setConfirmed(appointment.getConfirmed());
        appointmentDto.setUserDto(convertUserToUserDto(appointment.getUser()));
        return appointmentDto;
    }

    public static AppointmentDto  convertAppointmentToAppointmentDtoWithoutUser(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDescription(appointment.getDescription());
        appointmentDto.setLocation(appointment.getLocation());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setConfirmed(appointment.getConfirmed());
//        appointmentDto.setUserDto(appointment.getUser().convertUserToUserDto());
        return appointmentDto;
    }



    public static CheckingAccount convertCheckingAccountDtoToCheckingAccount(CheckingAccountDto checkingAccountDto){
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setId(checkingAccountDto.getId());
        checkingAccount.setAccountNumber(checkingAccountDto.getAccountNumber());
        checkingAccount.setAccountBalance(checkingAccountDto.getAccountBalance());
        checkingAccount.setCheckingTransactionList(getCheckingTransactionListByCheckingTransactionDtoList(checkingAccountDto.getCheckingTransactionDtoList()));
        return checkingAccount;
    }

    private static List<CheckingTransaction> getCheckingTransactionListByCheckingTransactionDtoList(List<CheckingTransactionDto> checkingTransactionDtoList) {
        List<CheckingTransaction> checkingTransactionList = new ArrayList<>();
        for(CheckingTransactionDto checkingTransactionDto : checkingTransactionDtoList) {
            CheckingTransaction checkingTransaction = convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
            checkingTransactionList.add(checkingTransaction);
        }
        return checkingTransactionList;
    }

    public static CheckingAccountDto convertCheckingAccountToCheckingAccountDto(CheckingAccount checkingAccount){
        CheckingAccountDto checkingAccountDto = new CheckingAccountDto();
        checkingAccountDto.setId(checkingAccount.getId());
        checkingAccountDto.setAccountNumber(checkingAccount.getAccountNumber());
        checkingAccountDto.setAccountBalance(checkingAccount.getAccountBalance());
        checkingAccountDto.setCheckingTransactionDtoList(getCheckingTransactionDtoListByCheckingTransactionList(checkingAccount.getCheckingTransactionList()));
        return checkingAccountDto;
    }

    private static List<CheckingTransactionDto> getCheckingTransactionDtoListByCheckingTransactionList(List<CheckingTransaction> checkingTransactionList) {
        List<CheckingTransactionDto> checkingTransactionDtoList = new ArrayList<>();
        for(CheckingTransaction checkingTransaction : checkingTransactionList) {
            CheckingTransactionDto checkingTransactionDto = convertCheckingTransactionToCheckingTransactionDto(checkingTransaction);
            checkingTransactionDtoList.add(checkingTransactionDto);
        }
        return checkingTransactionDtoList;
    }

    public static CheckingTransaction convertCheckingTransactionDtoToCheckingTransaction(CheckingTransactionDto checkingTransactionDto){
        CheckingTransaction checkingTransaction = new CheckingTransaction();
        checkingTransaction.setId(checkingTransactionDto.getId());
        checkingTransaction.setDate(checkingTransactionDto.getDate());
        checkingTransaction.setDescription(checkingTransactionDto.getDescription());
        checkingTransaction.setType(checkingTransactionDto.getType());
        checkingTransaction.setStatus(checkingTransactionDto.getStatus());
        checkingTransaction.setAmount(checkingTransactionDto.getAmount());
        checkingTransaction.setAvailableBalance(checkingTransactionDto.getAvailableBalance());
        checkingTransaction.setCheckingAccount(checkingTransactionDto.getCheckingAccount());
        return checkingTransaction;
    }

    public static CheckingTransactionDto convertCheckingTransactionToCheckingTransactionDto(CheckingTransaction checkingTransaction){
        CheckingTransactionDto checkingTransactionDto = new CheckingTransactionDto();
        checkingTransactionDto.setId(checkingTransaction.getId());
        checkingTransactionDto.setDate(checkingTransaction.getDate());
        checkingTransactionDto.setDescription(checkingTransaction.getDescription());
        checkingTransactionDto.setType(checkingTransaction.getType());
        checkingTransactionDto.setStatus(checkingTransaction.getStatus());
        checkingTransactionDto.setAmount(checkingTransaction.getAmount());
        checkingTransactionDto.setAvailableBalance(checkingTransaction.getAvailableBalance());
        checkingTransactionDto.setCheckingAccount(checkingTransaction.getCheckingAccount());
        return checkingTransactionDto;
    }




    public static SavingsAccount convertSavingsAccountDtoToSavingsAccount(SavingsAccountDto savingsAccountDto){
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(savingsAccountDto.getId());
        savingsAccount.setAccountNumber(savingsAccountDto.getAccountNumber());
        savingsAccount.setAccountBalance(savingsAccountDto.getAccountBalance());
        savingsAccount.setSavingsTransactionList(getSavingsTransactionListBySavingsTransactionDtoList(savingsAccountDto.getSavingsTransactionDtoList()));
        return savingsAccount;
    }

    private static List<SavingsTransaction> getSavingsTransactionListBySavingsTransactionDtoList(List<SavingsTransactionDto> savingsTransactionDtoList) {
        List<SavingsTransaction> savingsTransactionList = new ArrayList<>();
        for(SavingsTransactionDto savingsTransactionDto : savingsTransactionDtoList) {
            SavingsTransaction savingsTransaction = convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
            savingsTransactionList.add(savingsTransaction);
        }
        return savingsTransactionList;
    }

    public static SavingsAccountDto convertSavingsAccountToSavingsAccountDto(SavingsAccount savingsAccount){
        SavingsAccountDto savingsAccountDto = new SavingsAccountDto();
        savingsAccountDto.setId(savingsAccount.getId());
        savingsAccountDto.setAccountNumber(savingsAccount.getAccountNumber());
        savingsAccountDto.setAccountBalance(savingsAccount.getAccountBalance());
        savingsAccountDto.setSavingsTransactionDtoList(getSavingsTransactionDtoListBySavingsTransactionList(savingsAccount.getSavingsTransactionList()));
        return savingsAccountDto;
    }

    private static List<SavingsTransactionDto> getSavingsTransactionDtoListBySavingsTransactionList(List<SavingsTransaction> savingsTransactionList) {
        List<SavingsTransactionDto> savingsTransactionDtoList = new ArrayList<>();
        for(SavingsTransaction savingsTransaction : savingsTransactionList) {
            SavingsTransactionDto savingsTransactionDto = convertSavingsTransactionToSavingsTransactionDto(savingsTransaction);
            savingsTransactionDtoList.add(savingsTransactionDto);
        }
        return savingsTransactionDtoList;
    }

    public static SavingsTransaction convertSavingsTransactionDtoToSavingsTransaction(SavingsTransactionDto savingsTransactionDto){
        SavingsTransaction savingsTransaction = new SavingsTransaction();
        savingsTransaction.setId(savingsTransactionDto.getId());
        savingsTransaction.setDate(savingsTransactionDto.getDate());
        savingsTransaction.setDescription(savingsTransactionDto.getDescription());
        savingsTransaction.setType(savingsTransactionDto.getType());
        savingsTransaction.setStatus(savingsTransactionDto.getStatus());
        savingsTransaction.setAmount(savingsTransactionDto.getAmount());
        savingsTransaction.setAvailableBalance(savingsTransactionDto.getAvailableBalance());
        savingsTransaction.setSavingsAccount(savingsTransactionDto.getSavingsAccount());
        return savingsTransaction;
    }

    public static SavingsTransactionDto convertSavingsTransactionToSavingsTransactionDto(SavingsTransaction savingsTransaction){
        SavingsTransactionDto savingsTransactionDto = new SavingsTransactionDto();
        savingsTransactionDto.setId(savingsTransaction.getId());
        savingsTransactionDto.setDate(savingsTransaction.getDate());
        savingsTransactionDto.setDescription(savingsTransaction.getDescription());
        savingsTransactionDto.setType(savingsTransaction.getType());
        savingsTransactionDto.setStatus(savingsTransaction.getStatus());
        savingsTransactionDto.setAmount(savingsTransaction.getAmount());
        savingsTransactionDto.setAvailableBalance(savingsTransaction.getAvailableBalance());
        savingsTransactionDto.setSavingsAccount(savingsTransaction.getSavingsAccount());
        return savingsTransactionDto;
    }



    public static Recipient convertRecipientDtoToRecipient(RecipientDto recipientDto){
        Recipient recipient = new Recipient();
        recipient.setId(recipientDto.getId());
        recipient.setName(recipientDto.getName());
        recipient.setEmail(recipientDto.getEmail());
        recipient.setPhone(recipientDto.getPhone());
        recipient.setAccountNumber(recipientDto.getAccountNumber());
        recipient.setDescription(recipientDto.getDescription());
        recipient.setUser(DtoAndEntityConvertUtil.convertUserDtoToUser(recipientDto.getUserDto()));
        return recipient;
    }

    public static Recipient convertRecipientDtoToRecipientWithoutUser(RecipientDto recipientDto){
        Recipient recipient = new Recipient();
        recipient.setId(recipientDto.getId());
        recipient.setName(recipientDto.getName());
        recipient.setEmail(recipientDto.getEmail());
        recipient.setPhone(recipientDto.getPhone());
        recipient.setAccountNumber(recipientDto.getAccountNumber());
        recipient.setDescription(recipientDto.getDescription());
//        recipient.setUser(recipientDto.getUser());
        return recipient;
    }

    public static RecipientDto convertRecipientToRecipientDto(Recipient recipient){
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setId(recipient.getId());
        recipientDto.setName(recipient.getName());
        recipientDto.setEmail(recipient.getEmail());
        recipientDto.setPhone(recipient.getPhone());
        recipientDto.setAccountNumber(recipient.getAccountNumber());
        recipientDto.setDescription(recipient.getDescription());
        recipientDto.setUserDto(DtoAndEntityConvertUtil.convertUserToUserDto(recipient.getUser()));
        return recipientDto;
    }

    public static RecipientDto convertRecipientToRecipientDtoWithoutUser(Recipient recipient){
        RecipientDto recipientDto = new RecipientDto();
        recipientDto.setId(recipient.getId());
        recipientDto.setName(recipient.getName());
        recipientDto.setEmail(recipient.getEmail());
        recipientDto.setPhone(recipient.getPhone());
        recipientDto.setAccountNumber(recipient.getAccountNumber());
        recipientDto.setDescription(recipient.getDescription());
//        recipientDto.setUser(recipient.getUser());
        return recipientDto;
    }
}
