package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/professors")
    public Collection<User> getProfessors() {
        return userService.getProfessors();
    }

    @GetMapping("/api/teachers")
    public Collection<User> getTeachers() {
        return userService.getTeachers();
    }
}
