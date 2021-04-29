package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;

@Repository
public class StudentHibernateDao implements StudentDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public StudentHibernateDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Long insertStudent(Student student) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.persist(student);
            return student.getId();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Student student = entityManager.find(Student.class, Integer.valueOf(studentId));
            if (student != null) {
                entityManager.remove(student);
                return true;
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Student findStudent(String studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Student.class, Integer.valueOf(studentId));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Student dbStudent = entityManager.merge(student);
            return dbStudent != null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Student> selectStudents() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("Student.findAll", Student.class).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<Student> selectStudentsByGroup(int group) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("select s from Student s where group = :group", Student.class)
                    .setParameter("group", group).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
