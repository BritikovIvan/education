package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.service.AcademicDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
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

    @GetMapping("/api/disciplines/{id}")
    AcademicDiscipline findDiscipline(@PathVariable Long id) {
        return academicDisciplineService.getAcademicDiscipline(id);
    }

    @PostMapping("/api/disciplines")
    AcademicDiscipline addDiscipline(@RequestBody AcademicDiscipline discipline) {
        return academicDisciplineService.createAcademicDiscipline(discipline);
    }

    @PutMapping("/api/disciplines/{id}")
    AcademicDiscipline updateDiscipline(@PathVariable Long id ,@RequestBody AcademicDiscipline discipline) {
        return academicDisciplineService.editAcademicDiscipline(discipline, id);
    }
}
