package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface UserDAO {

    Long insertUser(User user);

    boolean deleteUser(String userId);

    User findUser(String userId);

    boolean updateUser(User user);

    Collection<User> selectUsers();

    User findUserByEmail(String email);

    Collection<User> selectProfessors();

    Collection<User> selectTeachers();
}
