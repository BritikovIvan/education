package by.educ.ivan.educationwebappboot.service;

import by.educ.ivan.educationwebappboot.model.LoginRequest;
import by.educ.ivan.educationwebappboot.model.LoginResponse;

public interface AuthenticationService {

    LoginResponse authenticateUser(LoginRequest request);

}
