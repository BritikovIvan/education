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

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public boolean updateUserStatus(User user) {
        return userDAO.updateUserStatus(user);
    }

    @Override
    public boolean updateUserRole(User user) {
        return userDAO.updateUserRole(user);
    }
}
