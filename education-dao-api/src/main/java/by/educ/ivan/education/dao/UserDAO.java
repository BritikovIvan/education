package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface UserDAO {

    int insertUser(User user);

    boolean deleteUser(String userId);

    User findUser(String userId);

    boolean updateUserName(String userId, String userFullName);

    Collection<User> selectUsers();

    User findUserByEmail(String email);

    boolean updateUserStatus(User user);

    boolean updateUserRole(User user);
}
