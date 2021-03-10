package by.educ.ivan;

import by.educ.ivan.education.dao.AcademicDisciplineDAO;
import by.educ.ivan.education.factory.DAOFactory;
import by.educ.ivan.education.model.AcademicDiscipline;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        DAOFactory mySqlDAOFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        AcademicDisciplineDAO disciplineDAO = mySqlDAOFactory.getAcademicDisciplineDAO();
        if(disciplineDAO != null){
            disciplineDAO.selectAcademicDisciplines();
            for (AcademicDiscipline item : disciplineDAO.selectAcademicDisciplines()) {
                System.out.println(item.getId() + " " + item.getName() + " " + item.getAbbreviation() + " ");
            }
        }
    }
}
