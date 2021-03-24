package by.educ.ivan;

import by.educ.ivan.education.dao.AcademicDisciplineDAO;
import by.educ.ivan.education.dao.AdminOperationDAO;
import by.educ.ivan.education.dao.UserDAO;
import by.educ.ivan.education.exception.AdminOperationException;
import by.educ.ivan.education.factory.DAOFactory;
import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.service.AdminOperationService;
import by.educ.ivan.education.service.AdminOperationServiceImpl;
import by.educ.ivan.education.service.UserService;
import by.educ.ivan.education.service.UserServiceImpl;


public class App {
    public static void main(String[] args) throws AdminOperationException {
        DAOFactory mySqlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        AcademicDisciplineDAO disciplineDAO = null;
        if (mySqlDAOFactory != null) {
            disciplineDAO = mySqlDAOFactory.getAcademicDisciplineDAO();
        }
        if (disciplineDAO != null) {
            disciplineDAO.selectAcademicDisciplines();
            for (AcademicDiscipline item : disciplineDAO.selectAcademicDisciplines()) {
                System.out.println(item.getId() + " " + item.getName() + " " + item.getAbbreviation() + " "
                        + item.getAuthor().getId());
            }
        }

        // AdminOperationService
        UserDAO userDAO = mySqlDAOFactory.getUserDAO();
        AdminOperationDAO operationDAO = mySqlDAOFactory.getAdminOperationDAO();
        UserService userService = new UserServiceImpl(userDAO);
        AdminOperationService adminOperationService = new AdminOperationServiceImpl(userService, operationDAO);
        AdminOperation adminOperation = new AdminOperation();
        adminOperationService.addAdminOperation(adminOperation);
    }
}
