package by.educ.ivan.education.service;

import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> getAllUsers();

    User createUser(User user);

    User getUserByEmail(String email);

    User updateUserStatus(User user, boolean isBlocked);

    User updateUserRole(User user, Role newRole);

    boolean isUserBlocked(User user);

    boolean isMagistralStaff(User user);

    User getUser(Long id);
}
