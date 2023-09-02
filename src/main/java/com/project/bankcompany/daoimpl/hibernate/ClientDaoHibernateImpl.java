package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.ClientDao;
import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

@Repository("ClientDaoHibernateImpl")
public class ClientDaoHibernateImpl implements ClientDao {

    private Logger logger = LoggerFactory.getLogger(ClientDaoHibernateImpl.class);

    @Override
    public Client save(Client client, Long managerId) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            if (managerId != null) {
                Manager manager = session.get(Manager.class, managerId);
                client.setManager(manager);
            }
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Fail to insert a Client, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return client;
    }

    @Override
    public Client update(Client client) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Fail to update a Client, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return client;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Client WHERE loginName = :loginName");
            query.setParameter("loginName", loginName);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            deleteResult = rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Fail to delete Client by login name, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public boolean deleteById(Long clientId) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            if (client != null) {
                session.delete(client);
                transaction.commit();
                deleteResult = true;
            }
        } catch (Exception e) {
            logger.error("Fail to delete Client by ID, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public boolean delete(Client client) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
            deleteResult = true;
        } catch (Exception e) {
            logger.error("Fail to delete Client, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public List<Client> getClients() {
        List<Client> clientList = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM Client");
            clientList = query.list();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve all clients, error = {}", he.getMessage());
        } finally {
            session.close();
        }

        if (clientList == null)
            clientList = new ArrayList<>();
        return clientList;
    }

    @Override
    public Client getClientById(Long id) {
        Client client = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            client = session.get(Client.class, id);
        } catch (HibernateException he) {
            logger.error("Fail to retrieve client with ID = {}, error = {}", id, he.getMessage());
        } finally {
            session.close();
        }
        return client;
    }

    @Override
    public Client getClientByLoginName(String loginName) {
        Client client = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM Client WHERE loginName = :loginName");
            query.setParameter("loginName", loginName);
            client = (Client) query.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve client with login name = {}, error = {}", loginName, he.getMessage());
        } finally {
            session.close();
        }
        return client;
    }

    @Override
    public List<Client> getClientsByManagerId(Long managerId) {
        return null;
    }

    @Override
    public List<Client> getClientsWithAssociatedProducts() {
        return null;
    }
}
