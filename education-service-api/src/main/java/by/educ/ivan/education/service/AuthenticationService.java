package by.educ.ivan.education.service;

import by.educ.ivan.education.model.User;

public interface AuthenticationService {
    User authenticateUser(String email, String password);

}
