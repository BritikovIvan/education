package by.educ.ivan.education.dao.factory;

import by.educ.ivan.education.dao.*;

public interface DaoFactory {
    AcademicDisciplineDAO getAcademicDisciplineDAO();

    AdminOperationDAO getAdminOperationDAO();

    EducationalMaterialDAO getEducationalMaterialDAO();

    StudentDAO getStudentDAO();

    StudyAssignmentDAO getStudyAssignmentDAO();

    UserDAO getUserDAO();
}
