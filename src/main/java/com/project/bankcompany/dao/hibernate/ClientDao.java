package com.project.bankcompany.dao.hibernate;



import com.project.bankcompany.entity.Client;
import java.util.List;

public interface ClientDao {

    Client save(Client client, Long managerId);
    Client update(Client client);
    boolean deleteByLoginName(String loginName);
    boolean deleteById(Long clientId);
    boolean delete(Client client);
    List<Client> getClients();
    Client getClientById(Long id);
    Client getClientByLoginName(String loginName);

    List<Client> getClientsByManagerId (Long managerId);
    //List<Project> getAssociatedProjectsByStudentId(Long studentId);
    //List<Project> getAssociatedProjectsByStudentLoginName(String loginName);

    List<Client> getClientsWithAssociatedProducts();
}
