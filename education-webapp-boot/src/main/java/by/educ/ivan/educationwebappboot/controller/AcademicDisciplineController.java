package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.service.AcademicDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AcademicDisciplineController {

    private final AcademicDisciplineService academicDisciplineService;

    @Autowired
    public AcademicDisciplineController(AcademicDisciplineService academicDisciplineService) {
        this.academicDisciplineService = academicDisciplineService;
    }

    @GetMapping("/api/disciplines")
    Collection<AcademicDiscipline> findAll() {
        return academicDisciplineService.getAllAcademicDisciplines();
    }
}
