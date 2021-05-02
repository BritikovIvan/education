package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.EducationalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EducationMaterialController {

    private final EducationalMaterialService materialService;

    @Autowired
    public EducationMaterialController(EducationalMaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/api/materials/teacher")
    public Collection<EducationalMaterial> findAllTeacherMaterials(@RequestParam("teacherId") Long id) {
        return materialService.getAllTeacherEducationalMaterials(id);
    }

    @GetMapping("/api/materials/professor")
    public Collection<EducationalMaterial> findAllProfessorMaterials(@RequestParam("professorId") Long id) {
        return materialService.getAllProfessorEducationalMaterials(id);
    }

    @GetMapping("/api/materials/{id}")
    public EducationalMaterial findMaterial(@PathVariable Long id) {
        return materialService.getEducationalDiscipline(id);
    }
}
