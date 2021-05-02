package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.service.AcademicDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AcademicDisciplineController {

    private final AcademicDisciplineService academicDisciplineService;

    @Autowired
    public AcademicDisciplineController(AcademicDisciplineService academicDisciplineService) {
        this.academicDisciplineService = academicDisciplineService;
    }

    @GetMapping("/api/disciplines")
    public Collection<AcademicDiscipline> findAll() {
        return academicDisciplineService.getAllAcademicDisciplines();
    }

    @GetMapping("/api/disciplines/{id}")
    public AcademicDiscipline findDiscipline(@PathVariable Long id) {
        return academicDisciplineService.getAcademicDiscipline(id);
    }

    @PostMapping("/api/disciplines")
    public AcademicDiscipline addDiscipline(@RequestBody AcademicDiscipline discipline) {
        return academicDisciplineService.createAcademicDiscipline(discipline);
    }

    @PatchMapping("/api/disciplines/{id}")
    public AcademicDiscipline updateDiscipline(@PathVariable Long id ,@RequestBody AcademicDiscipline discipline) {
        return academicDisciplineService.editAcademicDiscipline(discipline, id);
    }
}
