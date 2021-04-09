package by.educ.ivan.education.service;

import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

public class SessionServiceImpl implements SessionService, Runnable {

    private static final ThreadLocal<User> sessionUser = new ThreadLocal<User>() {
        @Override
        protected User initialValue() {
            User anonymous = new User();
            anonymous.setRole(Role.ANONYMOUS);
            return anonymous;
        }
    };

    @Override
    public User getCurrentUser() {
        return sessionUser.get();
    }

    @Override
    public void setUser(User user) {
        sessionUser.set(user);
    }

    @Override
    public void run() {
        
    }
}
