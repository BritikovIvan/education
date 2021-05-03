package by.educ.ivan.education.service;

import by.educ.ivan.education.model.User;

public interface SessionService {
    User getCurrentUser();

    boolean isProfessor();

    boolean isTeacher();

    void setCurrentUser(User user);
}
