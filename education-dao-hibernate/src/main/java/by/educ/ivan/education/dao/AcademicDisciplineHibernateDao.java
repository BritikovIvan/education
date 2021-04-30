package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public class AcademicDisciplineHibernateDao implements AcademicDisciplineDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AcademicDisciplineHibernateDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long insertAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.persist(academicDiscipline);
            entityManager.flush();
            return academicDiscipline.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteAcademicDiscipline(String academicDisciplineId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            AcademicDiscipline discipline = entityManager.find(AcademicDiscipline.class, Integer.valueOf(academicDisciplineId));
            if (discipline != null) {
                entityManager.remove(discipline);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AcademicDiscipline findAcademicDiscipline(String academicDisciplineId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(AcademicDiscipline.class, Long.valueOf(academicDisciplineId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            AcademicDiscipline discipline = entityManager.merge(academicDiscipline);
            return discipline != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<AcademicDiscipline> selectAcademicDisciplines() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("AcademicDiscipline.findAll", AcademicDiscipline.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
