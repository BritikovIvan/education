package by.educ.ivan.education.factory;

import by.educ.ivan.education.dao.AcademicDisciplineDAO;
import by.educ.ivan.education.dao.AdminOperationDAO;
import by.educ.ivan.education.dao.EducationalMaterialDAO;
import by.educ.ivan.education.dao.StudentDAO;
import by.educ.ivan.education.dao.StudyAssignmentDAO;
import by.educ.ivan.education.dao.UserDAO;
import by.educ.ivan.education.dao.factory.DaoFactory;

public abstract class DAOFactory implements DaoFactory {

    public static final int MYSQL = 1;

    public abstract AcademicDisciplineDAO getAcademicDisciplineDAO();

    public abstract AdminOperationDAO getAdminOperationDAO();

    public abstract EducationalMaterialDAO getEducationalMaterialDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract StudyAssignmentDAO getStudyAssignmentDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case MYSQL:
                return new MySQLDAOFactory();
            default:
                return null;
        }
    }
}
