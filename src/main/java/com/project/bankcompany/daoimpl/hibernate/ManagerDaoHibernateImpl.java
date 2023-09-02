package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.ManagerDao;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.util.HQLStatementUtil;
import com.project.bankcompany.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("ManagerDaoHibernateImpl")
public class ManagerDaoHibernateImpl implements ManagerDao {

    public List<Manager> managerList;
    private Logger logger = LoggerFactory.getLogger(ClientDaoHibernateImpl.class);

    @Override
    public Manager save(Manager manager) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.persist(manager);
            transaction.commit();
        }catch(Exception e){
            logger.error("fail to insert a Manager, error={}", e.getMessage());
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }

        return manager;
    }

    @Override
    public Manager update(Manager manager) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(manager);
            transaction.commit();
        }catch(Exception e){
            logger.error("fail to update a Manager, error={}", e.getMessage());
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }

        return manager;
    }

    @Override
    public boolean deleteByName(String managerName) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        int deletedCount = session.createQuery("DELETE FROM Manager m WHERE m.name = :name")
                .setParameter("name", managerName)
                .executeUpdate();

        transaction.commit();
        session.close();

        return deletedCount > 0;
    }

    @Override
    public boolean deleteById(Long managerId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Manager manager = session.get(Manager.class, managerId);
        if (manager != null) {
            session.delete(manager);
            transaction.commit();
            session.close();
            return true;
        } else {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    @Override
    public boolean delete(Manager manager) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();
            session.delete(manager);
            transaction.commit();
            deleteResult = true;
        }catch(Exception e){
            logger.error("fail to delete Manager, error={}", e.getMessage());
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public List<Manager> getManagers() {
        List<Manager> managerList = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Manager> query = session.createQuery(HQLStatementUtil.HQL_SELECT_ALL_MANAGERS);
            managerList = query.list();
        }catch (HibernateException he){
            logger.error("fail to retrieve all managers error = {}", he.getMessage());
        }finally {
            session.close();
        }

        if(managerList == null)
            managerList = new ArrayList<>();
        return managerList;
    }

    @Override
    public Manager getManagerById(Long managerId) {
        Manager manager = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Manager> query = session.createQuery(HQLStatementUtil.HQL_SELECT_MANAGER_BY_ID);
            query.setParameter("id",managerId);
            manager = query.uniqueResult();
        }catch (HibernateException he){
            logger.error("fail to retrieve manager with id = {}, error = {}", managerId, he.getMessage());
        }finally {
            session.close();
        }
        return manager;
    }

    @Override
    public Manager getManagerAndClientAndProductWithManagerId(Long managerId) {
        Manager manager = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Manager> query = session.createQuery(HQLStatementUtil.HQL_SELECT_MANAGER_WITH_CLIENTS_BY_MANAGER_ID);
            query.setParameter("id",managerId);
            manager = query.uniqueResult();
        }catch (HibernateException he){
            logger.error("fail to retrieve manager with id = {}, error = {}", managerId, he.getMessage());
        }finally {
            session.close();
        }
        return manager;
    }

    @Override
    public Manager getManagerByName(String managerName) {
        Manager manager = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Manager> query = session.createQuery(HQLStatementUtil.HQL_SELECT_MANAGER_BY_NAME);
            query.setParameter("name",managerName);
            manager = query.uniqueResult();
        }catch (HibernateException he){
            logger.error("fail to retrieve manager with name = {}, error = {}", managerName, he.getMessage());
        }finally {
            session.close();
        }
        return manager;
    }
}
