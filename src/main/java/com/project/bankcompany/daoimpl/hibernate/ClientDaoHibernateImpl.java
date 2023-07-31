package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.hibernate.ClientDao;
import com.project.bankcompany.entity.Client;
import com.project.bankcompany.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("ClientDaoHibernateImpl")
public class ClientDaoHibernateImpl implements ClientDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public Client save(Client client, Long managerId) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            logger.error("fail to insert a Client, error={}", e.getMessage());
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
            logger.error("fail to insert a Client, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return client;

    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Client WHERE loginName = :loginName");
            query.setParameter("loginName", loginName);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Failed to delete Client by loginName, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return false;
    }

    @Override
    public boolean deleteById(Long clientId) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            if (client != null) {
                session.delete(client);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            logger.error("Failed to delete Client by ID, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return false;
    }


    @Override
    public boolean delete(Client client) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete Client, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return false;
    }


    @Override
    public List<Client> getClients() {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            List<Client> clients = session.createQuery("FROM Client", Client.class).getResultList();
            transaction.commit();
            return clients;
        } catch (Exception e) {
            logger.error("Failed to fetch Clients, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }


    @Override
    public Client getClientById(Long id) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, id);
            transaction.commit();
            return client;
        } catch (Exception e) {
            logger.error("Failed to fetch Client by ID, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }


    @Override
    public Client getClientByLoginName(String loginName) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Client WHERE loginName = :loginName");
            query.setParameter("loginName", loginName);
            List<Client> clients = query.getResultList();
            transaction.commit();

            return clients.isEmpty() ? null : clients.get(0);
        } catch (Exception e) {
            logger.error("Failed to fetch Client by loginName, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }



    @Override
    public List<Client> getClientsByManagerId(Long managerId) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Client WHERE manager.id = :managerId");
            query.setParameter("managerId", managerId);
            List<Client> clients = query.getResultList();
            transaction.commit();

            return clients;
        } catch (Exception e) {
            logger.error("Failed to fetch Clients by Manager ID, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }



    @Override
    public List<Client> getClientsWithAssociatedProducts() {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.productList");
            List<Client> clients = query.getResultList();
            transaction.commit();

            return clients;
        } catch (Exception e) {
            logger.error("Failed to fetch Clients with associated Products, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return null;
    }



}
