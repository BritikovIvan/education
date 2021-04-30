package by.educ.ivan.educationwebappboot.controller;

import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.EducationalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class EducationMaterialController {

    private final EducationalMaterialService materialService;

    @Autowired
    public EducationMaterialController(EducationalMaterialService materialService) {
        this.materialService = materialService;
    }

    @CrossOrigin
    @GetMapping("/api/materials?authorId={id}")
    Collection<EducationalMaterial> findAllTeacherMaterials(@PathVariable Long id) {
        return materialService.getAllTeacherEducationalMaterials(new User());
    }

    @GetMapping("/api/materials?reviewerId={id}")
    Collection<EducationalMaterial> findAllProfessorMaterials(@PathVariable Long id) {
        return materialService.getAllProfessorEducationalMaterials(new User());
    }
}
