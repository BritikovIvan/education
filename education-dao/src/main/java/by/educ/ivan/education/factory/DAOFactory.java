package by.educ.ivan.education.factory;

import by.educ.ivan.education.dao.*;

public abstract class DAOFactory {

    // List of DAO types supported by the factory
    public static final int MYSQL = 1;

    // There will be a method for each DAO that can be
    // created. The concrete factories will have to
    // implement these methods.
    public abstract AcademicDisciplineDAO getAcademicDisciplineDAO();

    public abstract AdminOperationDAO getAdminOperationDAO();

    public abstract EducationalMaterialDAO getEducationalMaterialDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract StudyAssignmentDAO getStudyAssignmentDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int whichFactory) {

        switch (whichFactory) {
            case MYSQL:
                return new MySQLDAOFactory();//MySQLDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
