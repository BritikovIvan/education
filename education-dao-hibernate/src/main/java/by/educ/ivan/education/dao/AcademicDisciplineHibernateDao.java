package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class AcademicDisciplineHibernateDao implements AcademicDisciplineDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AcademicDiscipline insertAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        try {
            entityManager.persist(academicDiscipline);
            return academicDiscipline;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteAcademicDiscipline(String academicDisciplineId) {
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
        try {
            return entityManager.find(AcademicDiscipline.class, Long.valueOf(academicDisciplineId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public AcademicDiscipline updateAcademicDiscipline(AcademicDiscipline academicDiscipline) {
        try {
            return entityManager.merge(academicDiscipline);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<AcademicDiscipline> selectAcademicDisciplines() {
        try {
            return entityManager.createNamedQuery("AcademicDiscipline.findAll", AcademicDiscipline.class).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
