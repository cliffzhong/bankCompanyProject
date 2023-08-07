package com.project.bankcompany.daoimpl.sproingdatajpa;


import com.project.bankcompany.dao.hibernate.ManagerDao;
import com.project.bankcompany.daoimpl.repository.ManagerRepository;
import com.project.bankcompany.entity.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("ManagerDaoSpringDataJPAImpl")
public class ManagerDaoSpringDataJPAImpl implements ManagerDao {

    private Logger logger = LoggerFactory.getLogger(ManagerDaoSpringDataJPAImpl.class);

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager save(Manager manager) {
        Manager savedManager = managerRepository.save(manager);
        return savedManager;
    }

    @Override
    public Manager update(Manager manager) {
        Manager updateManager = managerRepository.save(manager);
        return updateManager;
    }

    @Override
    public boolean deleteByName(String clientManagerName) {
        boolean successFlag = false;
        try{
            Long deleteResult = managerRepository.deleteByName(clientManagerName);
            if(deleteResult > 0L){
                successFlag = true;
            }
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteByName with ManagerName={}, error={}",clientManagerName, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteByName with ManagerName={}, error={}",clientManagerName, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public boolean deleteById(Long managerId) {
        boolean successFlag = false;
        try{
            managerRepository.deleteById(managerId);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with ManagerId={}, error={}",managerId, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with ManagerId={}, error={}",managerId, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public boolean delete(Manager manager) {
        boolean successFlag = false;
        if(manager != null){
            successFlag = deletingManager(manager);
        }
        return successFlag;
    }

    private boolean deletingManager(Manager manager) {
        boolean deleteFlag = false;
        try{
            Optional<Manager> optionalManager = managerRepository.findById(manager.getId());
            if(optionalManager.isPresent()){
                managerRepository.delete(manager);
                deleteFlag = true;
            }
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying delete with Manager={}, error={}",manager, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying delete with Manager={}, error={}",manager, olfe.getMessage());
        }
        return deleteFlag;
    }

    @Override
    public List<Manager> getManagers() {
        List<Manager> managerList =  managerRepository.findAll();
        if(managerList == null)
            managerList = new ArrayList<Manager>();
        return managerList;
    }

    @Override
    public Manager getManagerById(Long id) {
        Manager manager = null;
        Optional<Manager> managerOptional = managerRepository.findById(id);
        if(managerOptional.isPresent())
            manager = managerOptional.get();
        return manager;
    }

    @Override
    public Manager getManagerByName(String managerName) {
        Manager manager = null;
        try {
            manager = managerRepository.findByName(managerName);
        } catch (IllegalArgumentException iae) {
            logger.error("Caught IllegalArgumentException when trying to find Manager by name={}, error={}", managerName, iae.getMessage());
        } catch (OptimisticLockingFailureException olfe) {
            logger.error("Caught OptimisticLockingFailureException when trying to find Manager by name={}, error={}", managerName, olfe.getMessage());
        }
        return manager;
    }


    @Override
    public Manager getManagerAndClientAndProductWithManagerId(Long id) {
        Manager manager = null;
        try {
            manager = managerRepository.findByIdWithClientsAndProducts(id);
        } catch (IllegalArgumentException iae) {
            logger.error("Caught IllegalArgumentException when trying to find Manager with clients and products by id={}, error={}", id, iae.getMessage());
        } catch (OptimisticLockingFailureException olfe) {
            logger.error("Caught OptimisticLockingFailureException when trying to find Manager with clients and products by id={}, error={}", id, olfe.getMessage());
        }
        return manager;
    }

}
