package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.hibernate.ClientDao;
import com.project.bankcompany.entity.Client;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientDaoHibernateTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private ClientDaoHibernateImpl clientDaoHibernate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testSaveClient() {
        Client client = createClientByName("abc", 18);
        doAnswer((Answer<Void>) invocation -> {
            client.setId(1L);
            return null;
        }).when(session).persist(client);

        Client savedClient = clientDaoHibernate.save(client, 12L);

        assertNotNull(savedClient.getId());
        assertEquals(client.getFirstName(), savedClient.getFirstName());
        assertEquals(client.getLastName(), savedClient.getLastName());

        verify(session, times(1)).persist(client);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testUpdateClient() {
        Client client = createClientByName("abc", 12);
        doNothing().when(session).saveOrUpdate(client);

        Client updatedClient = clientDaoHibernate.update(client);

        assertNotNull(updatedClient);
        assertEquals(client.getFirstName(), updatedClient.getFirstName());

        verify(session, times(1)).saveOrUpdate(client);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testDeleteByLoginName() {
        String loginName = "user123";
        Query query = mock(Query.class);
        when(session.createQuery("DELETE FROM Client WHERE loginName = :loginName")).thenReturn(query);
        when(query.setParameter("loginName", loginName)).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean isDeleted = clientDaoHibernate.deleteByLoginName(loginName);

        assertTrue(isDeleted);

        verify(session, times(1)).createQuery("DELETE FROM Client WHERE loginName = :loginName");
        verify(query, times(1)).setParameter("loginName", loginName);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testDeleteById() {
        Long clientId = 1L;
        Client client = createClientByName("abc", clientId);
        when(session.get(Client.class, clientId)).thenReturn(client);

        boolean isDeleted = clientDaoHibernate.deleteById(clientId);

        assertTrue(isDeleted);

        verify(session, times(1)).delete(client);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testDeleteClient() {
        Client client = createClientByName("abc", 12);
        doNothing().when(session).delete(client);

        boolean isDeleted = clientDaoHibernate.delete(client);

        assertTrue(isDeleted);

        verify(session, times(1)).delete(client);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testGetClients() {
        Query query = mock(Query.class);
        List<Client> clients = List.of(createClientByName("abc", 1), createClientByName("xyz", 2));
        when(session.createQuery("FROM Client", Client.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(clients);

        List<Client> retrievedClients = clientDaoHibernate.getClients();

        assertEquals(clients.size(), retrievedClients.size());

        verify(session, times(1)).createQuery("FROM Client", Client.class);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testGetClientById() {
        Long clientId = 1L;
        Client client = createClientByName("abc", clientId);
        when(session.get(Client.class, clientId)).thenReturn(client);

        Client retrievedClient = clientDaoHibernate.getClientById(clientId);

        assertNotNull(retrievedClient);
        assertEquals(clientId, retrievedClient.getId());

        verify(session, times(1)).get(Client.class, clientId);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testGetClientByLoginName() {
        String loginName = "user123";
        List<Client> clients = List.of(createClientByName("John", 1));
        Query query = mock(Query.class);
        when(session.createQuery("FROM Client WHERE loginName = :loginName", Client.class)).thenReturn(query);
        when(query.setParameter("loginName", loginName)).thenReturn(query);
        when(query.getResultList()).thenReturn(clients);

        Client retrievedClient = clientDaoHibernate.getClientByLoginName(loginName);

        assertNotNull(retrievedClient);
        assertEquals(loginName, retrievedClient.getLoginName());

        verify(session, times(1)).createQuery("FROM Client WHERE loginName = :loginName", Client.class);
        verify(query, times(1)).setParameter("loginName", loginName);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testGetClientsByManagerId() {
        Long managerId = 12L;
        List<Client> clients = List.of(createClientByName("abc", 1), createClientByName("xyz", 2));
        Query query = mock(Query.class);
        when(session.createQuery("FROM Client WHERE manager.id = :managerId", Client.class)).thenReturn(query);
        when(query.setParameter("managerId", managerId)).thenReturn(query);
        when(query.getResultList()).thenReturn(clients);

        List<Client> retrievedClients = clientDaoHibernate.getClientsByManagerId(managerId);

        assertEquals(clients.size(), retrievedClients.size());

        verify(session, times(1)).createQuery("FROM Client WHERE manager.id = :managerId", Client.class);
        verify(query, times(1)).setParameter("managerId", managerId);
        verify(transaction, times(1)).commit();
    }

    @Test
    public void testGetClientsWithAssociatedProducts() {
        List<Client> clients = List.of(
                createClientByName("abc", 1),
                createClientByName("xyz", 2)
        );

        Query query = mock(Query.class);
        when(session.createQuery("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.productList", Client.class))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(clients);

        List<Client> clientsWithProducts = clientDaoHibernate.getClientsWithAssociatedProducts();
        assertEquals(2, clientsWithProducts.size());

        verify(session, times(1)).createQuery("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.productList", Client.class);
        verify(transaction, times(1)).commit();
    }

    // Utility method to create a test client with the specified name and ID
    private Client createClientByName(String firstName, long id) {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(firstName + "cde");
        client.setLoginName("1223");
        client.setEmail("1223");
        client.setEnrolledDate(LocalDate.now());
        return client;
    }
}
