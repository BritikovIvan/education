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
    public User login(String email, String password) {
        try {
            User user = userService.getUserByEmail(email);
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                setUser(user);
                user.setPassword(null);
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
//        return null;
    }

    @Override
    public User getCurrentUser() {
        return sessionUser.get();
    }


    private void setUser(User user) {
        sessionUser.set(user);
    }

    public boolean isProfessor() {
        return getCurrentUser().getRole() == Role.PROFESSOR;
    }

    public boolean isTeacher() {
        return getCurrentUser().getRole() == Role.TEACHER;
    }

}
