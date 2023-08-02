package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.hibernate.ProductDao;
import com.project.bankcompany.entity.Product;
import com.project.bankcompany.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("ProductDaoHibernateImpl")
public class ProductDaoHibernateImpl implements ProductDao {

    private Logger logger = LoggerFactory.getLogger(ProductDaoHibernateImpl.class);

    @Override
    public Product save(Product product) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Fail to insert a Product, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return product;
    }

    @Override
    public Product update(Product product) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(product);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Fail to update a Product, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return product;
    }

    @Override
    public boolean deleteByName(String productName) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Product WHERE name = :name");
            query.setParameter("name", productName);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            deleteResult = rowsAffected > 0;
        } catch (Exception e) {
            logger.error("Fail to delete Product by name, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public boolean deleteById(Long productId) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, productId);
            if (product != null) {
                session.delete(product);
                transaction.commit();
                deleteResult = true;
            }
        } catch (Exception e) {
            logger.error("Fail to delete Product by ID, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public boolean delete(Product product) {
        boolean deleteResult = false;
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            session.delete(product);
            transaction.commit();
            deleteResult = true;
        } catch (Exception e) {
            logger.error("Fail to delete Product, error={}", e.getMessage());
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return deleteResult;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> productList = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM Product");
            productList = query.list();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve all products, error = {}", he.getMessage());
        } finally {
            session.close();
        }

        if (productList == null)
            productList = new ArrayList<>();
        return productList;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            product = session.get(Product.class, id);
        } catch (HibernateException he) {
            logger.error("Fail to retrieve product with ID = {}, error = {}", id, he.getMessage());
        } finally {
            session.close();
        }
        return product;
    }

    // ... (previous methods)

    @Override
    public Product getProductByName(String productName) {
        Product product = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Product> query = session.createQuery("FROM Product WHERE name = :name", Product.class);
            query.setParameter("name", productName);
            product = query.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve product with name = {}, error = {}", productName, he.getMessage());
        } finally {
            session.close();
        }
        return product;
    }

    @Override
    public List<Product> getProductsWithAssociatedClients() {
        List<Product> productsWithClients = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Product> query = session.createQuery("FROM Product p JOIN FETCH p.clientList", Product.class);
            productsWithClients = query.list();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve products with associated clients, error = {}", he.getMessage());
        } finally {
            session.close();
        }
        return productsWithClients;
    }

    @Override
    public Product getProductsWithAssociatedClientsById(Long productId) {
        Product product = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Product> query = session.createQuery(
                    "FROM Product p JOIN FETCH p.clientList WHERE p.id = :id",
                    Product.class
            );
            query.setParameter("id", productId);
            product = query.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve product with ID = {} and associated clients, error = {}", productId, he.getMessage());
        } finally {
            session.close();
        }
        return product;
    }

    @Override
    public Product getProductsWithAssociatedClientsByName(String productName) {
        Product product = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            Query<Product> query = session.createQuery(
                    "FROM Product p JOIN FETCH p.clientList WHERE p.name = :name",
                    Product.class
            );
            query.setParameter("name", productName);
            product = query.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Fail to retrieve product with name = {} and associated clients, error = {}", productName, he.getMessage());
        } finally {
            session.close();
        }
        return product;
    }
}

