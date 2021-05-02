package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class EducationalMaterialHibernateDao implements EducationalMaterialDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long insertEducationalMaterial(EducationalMaterial educationalMaterial) {
        try {
            entityManager.persist(educationalMaterial);
            return educationalMaterial.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteEducationalMaterial(String educationalMaterialId) {
        try {
            EducationalMaterial material = entityManager.find(EducationalMaterial.class, Integer.valueOf(educationalMaterialId));
            if (material != null) {
                entityManager.remove(material);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public EducationalMaterial findEducationalMaterial(String educationalMaterialId) {
        try {
            return entityManager.find(EducationalMaterial.class, Long.valueOf(educationalMaterialId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterials() {
        try {
            return entityManager.createNamedQuery("EducationalMaterial.findAll", EducationalMaterial.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByReviewer(User professor) {
        try {
            return entityManager.createQuery("select distinct e from EducationalMaterial e where (e.reviewer = :reviewer and reviewStatus <> 'DRAFT') " +
                    "or (e.academicDiscipline in (select a.id from AcademicDiscipline a where a.author = :reviewer) and reviewStatus <> 'DRAFT') ", EducationalMaterial.class)
                    .setParameter("reviewer", professor).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByTeacher(User teacher) {
        try {
            return entityManager.createQuery("select e from EducationalMaterial e where e.author = :author", EducationalMaterial.class)
                    .setParameter("author", teacher).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateEducationalMaterial(EducationalMaterial material) {
        try {
            EducationalMaterial educationalMaterial = entityManager.merge(material);
            return educationalMaterial != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByDiscipline(AcademicDiscipline discipline) {
        try {
            return entityManager.createQuery("select e from EducationalMaterial e where e.academicDiscipline = :discipline", EducationalMaterial.class)
                    .setParameter("discipline", discipline).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
