package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AdminOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public class AdminOperationHibernateDao implements AdminOperationDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AdminOperationHibernateDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long insertAdminOperation(AdminOperation adminOperation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.persist(adminOperation);
            return adminOperation.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteAdminOperation(String adminOperationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            AdminOperation operation = entityManager.find(AdminOperation.class, Integer.valueOf(adminOperationId));
            if (operation != null) {
                entityManager.remove(operation);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AdminOperation findAdminOperation(String adminOperationId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(AdminOperation.class, Integer.valueOf(adminOperationId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateAdminOperation(AdminOperation adminOperation) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            AdminOperation operation = entityManager.merge(adminOperation);
            return operation != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<AdminOperation> selectAdminOperations() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("AdminOperation.findAll", AdminOperation.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
