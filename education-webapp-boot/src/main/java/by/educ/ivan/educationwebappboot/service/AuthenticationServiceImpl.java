package by.educ.ivan.educationwebappboot.service;

import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.UserService;
import by.educ.ivan.educationwebappboot.model.LoginRequest;
import by.educ.ivan.educationwebappboot.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        if (user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(user.getId().toString(), user);
        } else {
            return null;
        }
    }
}
