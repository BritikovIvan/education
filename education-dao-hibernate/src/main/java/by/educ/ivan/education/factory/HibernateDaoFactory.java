package by.educ.ivan.education.factory;

import by.educ.ivan.education.dao.*;
import by.educ.ivan.education.dao.factory.DaoFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

public class HibernateDaoFactory implements DaoFactory {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public HibernateDaoFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public AcademicDisciplineDAO getAcademicDisciplineDAO() {
        //return new AcademicDisciplineHibernateDao(entityManagerFactory);
        return null;
    }

    @Override
    public AdminOperationDAO getAdminOperationDAO() {
        return new AdminOperationHibernateDao(entityManagerFactory);
    }

    @Override
    public EducationalMaterialDAO getEducationalMaterialDAO() {
//        return new EducationalMaterialHibernateDao(entityManagerFactory);
        return null;
    }

    @Override
    public StudentDAO getStudentDAO() {
        return new StudentHibernateDao(entityManagerFactory);
    }

    @Override
    public StudyAssignmentDAO getStudyAssignmentDAO() {
        return new StudyAssignmentHibernateDao(entityManagerFactory);
    }

    @Override
    public UserDAO getUserDAO() {
//        return new UserHibernateDao(entityManagerFactory);
        return null;
    }
}
