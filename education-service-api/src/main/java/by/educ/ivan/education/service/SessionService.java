package by.educ.ivan.education.service;

import by.educ.ivan.education.model.User;

public interface SessionService {
    User getCurrentUser();

    void setUser(User user);
}
