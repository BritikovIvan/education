package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.SessionService;
import by.educ.ivan.educationwebappboot.model.LoginRequest;
import by.educ.ivan.educationwebappboot.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthorizationController {

    private final SessionService sessionService;

    @Autowired
    public AuthorizationController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = sessionService.login(request.getEmail(), request.getPassword());
        return new LoginResponse("token", user);
    }
}
