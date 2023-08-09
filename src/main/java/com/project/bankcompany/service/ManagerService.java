package com.project.bankcompany.service;



import com.project.bankcompany.dto.ManagerDto;

import java.util.List;

public interface ManagerService {

    ManagerDto save(ManagerDto managerDto);

    ManagerDto update(ManagerDto managerDto);

    boolean deleteByName(String managerDtoName);

    boolean deleteById(Long managerDtoId);

    boolean delete(ManagerDto managerDto);

    List<ManagerDto> getManagers();

    ManagerDto getManagerById(Long id);

    ManagerDto getManagerByName(String managerDtoName);
}
