package by.educ.ivan.education.service;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private final UserService userService;

    private static final ThreadLocal<User> sessionUser = new ThreadLocal<User>() {
        @Override
        protected User initialValue() {
            User anonymous = new User();
            anonymous.setRole(Role.ANONYMOUS);
            return anonymous;
        }
    };

    @Autowired
    public SessionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getCurrentUser() {
        return sessionUser.get();
    }

    @Override
    public void setCurrentUser(User user) {
        sessionUser.set(user);
    }

    public boolean isProfessor() {
        return getCurrentUser().getRole() == Role.PROFESSOR;
    }

    public boolean isTeacher() {
        return getCurrentUser().getRole() == Role.TEACHER;
    }

}
