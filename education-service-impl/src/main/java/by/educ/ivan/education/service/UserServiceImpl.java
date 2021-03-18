package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.UserDAO;
import by.educ.ivan.education.model.User;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public Collection<User> getAllUsers() {
        return userDAO.selectUsers();
    }

    @Override
    public User createUser(User user) {
        userDAO.insertUser(user);
        return user;
    }

}
