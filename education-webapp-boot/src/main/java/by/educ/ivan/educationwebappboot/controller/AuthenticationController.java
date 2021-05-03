package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.educationwebappboot.model.LoginRequest;
import by.educ.ivan.educationwebappboot.model.LoginResponse;
import by.educ.ivan.educationwebappboot.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authenticationService.authenticateUser(request);
    }
}
