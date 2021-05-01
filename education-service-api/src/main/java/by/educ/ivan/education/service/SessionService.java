package by.educ.ivan.education.service;

import by.educ.ivan.education.model.User;

public interface SessionService {
    User getCurrentUser();

    User login(String email, String password);

    boolean isProfessor();

    boolean isTeacher();
}
