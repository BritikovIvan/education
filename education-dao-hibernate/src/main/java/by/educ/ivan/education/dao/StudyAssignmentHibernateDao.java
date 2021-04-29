package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;
import by.educ.ivan.education.model.StudyAssignment;
import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public class StudyAssignmentHibernateDao implements StudyAssignmentDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public StudyAssignmentHibernateDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long insertStudyAssignment(StudyAssignment studyAssignment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.persist(studyAssignment);
            return studyAssignment.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteStudyAssignment(String studyAssignmentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            StudyAssignment assignment = entityManager.find(StudyAssignment.class, Integer.valueOf(studyAssignmentId));
            if (assignment != null) {
                entityManager.remove(assignment);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public StudyAssignment findStudyAssignment(String studyAssignmentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(StudyAssignment.class, Integer.valueOf(studyAssignmentId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateStudyAssignment(StudyAssignment studyAssignment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            StudyAssignment assignment = entityManager.merge(studyAssignment);
            return assignment != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignments() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("StudyAssignment.findAll", StudyAssignment.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignmentsByAuthor(User author) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select s from StudyAssignment s where s.teacher = :teacher", StudyAssignment.class)
                    .setParameter("teacher", author).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignmentsByStudent(Student student) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select s from StudyAssignment s where s.student = :student", StudyAssignment.class)
                    .setParameter("student", student).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
