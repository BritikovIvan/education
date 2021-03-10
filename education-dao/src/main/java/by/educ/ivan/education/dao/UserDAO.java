package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface UserDAO {
    int insertUser(User user);

    boolean deleteUser(String userId);

    User findUser(String userId);

    boolean updateUser(String userId, String  userFullName);

    Collection<User> selectUsers();
}
