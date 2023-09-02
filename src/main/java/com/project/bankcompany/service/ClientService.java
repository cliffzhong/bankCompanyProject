package com.project.bankcompany.service;



import com.project.bankcompany.dto.ClientDto;
import com.project.bankcompany.entity.Client;

import java.util.List;

public interface ClientService {


    ClientDto signUp(ClientDto clientDto, Long managerId);

    ClientDto signIn(String loginName, String password);

    ClientDto save(ClientDto clientDto, Long managerId);

    ClientDto update(ClientDto clientDto);

    boolean deleteByLoginName(String loginName);

    boolean deleteById(Long clientDtoId);

    boolean delete(ClientDto clientDto);

    List<ClientDto> getClients();

    ClientDto getClientById(Long id);

    ClientDto getClientByLoginName(String loginName);
}
