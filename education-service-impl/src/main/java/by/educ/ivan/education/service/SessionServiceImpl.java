package by.educ.ivan.education.service;

import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl<sessionUser> implements SessionService {

//    private static final ThreadLocal<User> sessionUser = new ThreadLocal<User>() {
//        @Override
//        protected User initialValue() {
//            User anonymous = new User();
//            anonymous.setRole(Role.ANONYMOUS);
//            return anonymous;
//        }
//    };

    private final User sessionUser = new User();

    @Override
    public User getCurrentUser() {
        return sessionUser;
    }

    @Override
    public void setUser(User user) {

    }

    //    @Override
//    public User getCurrentUser() {
//        return sessionUser.get();
//    }
//
//    @Override
//    public void setUser(User user) {
//        sessionUser.set(user);
//    }

}
