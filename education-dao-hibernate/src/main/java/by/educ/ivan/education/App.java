package by.educ.ivan.education;

import by.educ.ivan.education.dao.UserDAO;
import by.educ.ivan.education.factory.HibernateDaoFactory;
import by.educ.ivan.education.model.User;

import javax.persistence.Persistence;
import java.util.Collection;

public class App {
    public static void main(String[] args) {
        HibernateDaoFactory hibernateDaoFactory = new HibernateDaoFactory(Persistence.createEntityManagerFactory("education"));
        UserDAO userDAO = hibernateDaoFactory.getUserDAO();
        Collection<User> users = userDAO.selectUsers();
        for (User user : users) {
            System.out.println(print(user));
        }
    }

    private static String print(User user) {
        return user.getId() + "\t" + user.getFullName() + "\t" + user.getEmail() + "\t" + user.getRole() + "\n";
    }
}
