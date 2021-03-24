package by.educ.ivan.education.service;

import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> getAllUsers();

    User createUser(User user);

    User getUserByEmail(String email);

    boolean updateUserStatus(User user);

    boolean updateUserRole(User user);
}
