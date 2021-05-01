package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public class UserHibernateDao implements UserDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserHibernateDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long insertUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.persist(user);
            return user.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            User user = entityManager.find(User.class, Integer.valueOf(userId));
            if (user != null) {
                entityManager.remove(user);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUser(String userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(User.class, Long.valueOf(userId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            User dbUser = entityManager.merge(user);
            return dbUser != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<User> selectUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUserByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select u from User u where email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
        } finally {
            entityManager.close();
        }
    }
}
