package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.UserDAO;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final SessionService sessionService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, SessionService sessionService) {
        this.userDAO = userDAO;
        this.sessionService = sessionService;
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDAO.selectUsers();
    }

    @Override
    public User createUser(User user) {
        user.setId(userDAO.insertUser(user));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public User updateUserStatus(User user, boolean isBlocked) {
        User updateUser = userDAO.findUser(String.valueOf(user.getId()));
        updateUser.setBlocked(isBlocked);
        userDAO.updateUser(updateUser);
        return updateUser;
    }

    @Override
    public User updateUserRole(User user, Role newRole) {
        User updateUser = userDAO.findUser(String.valueOf(user.getId()));
        updateUser.setRole(newRole);
        userDAO.updateUser(updateUser);
        return updateUser;
    }

    @Override
    public boolean isUserBlocked(User user) {
        return getUserByEmail(user.getEmail()).isBlocked();
    }

    @Override
    public boolean isProfessor() {
        return sessionService.getCurrentUser().getRole() == Role.PROFESSOR;
    }

    @Override
    public boolean isTeacher() {
        return sessionService.getCurrentUser().getRole() == Role.TEACHER;
    }

    @Override
    public boolean isMagistralStaff(User user) {
        return user.getRole() == Role.PROFESSOR || user.getRole() == Role.TEACHER;
    }

    @Override
    public User getUser(Long id) {
        return userDAO.findUser(String.valueOf(id));
    }


}
