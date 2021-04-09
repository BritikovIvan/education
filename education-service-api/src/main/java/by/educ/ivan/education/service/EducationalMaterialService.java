package by.educ.ivan.education.service;

import by.educ.ivan.education.model.*;

import java.util.Collection;

public interface EducationalMaterialService {

    Collection<EducationalMaterial> getAllProfessorEducationalMaterials(User professor);

    Collection<EducationalMaterial> getAllTeacherEducationalMaterials(User teacher);

    Collection<EducationalMaterial> getAllEducationalMaterialsByDiscipline(AcademicDiscipline discipline);

    EducationalMaterial createEducationalMaterial(EducationalMaterial educationalMaterial);

    EducationalMaterial editEducationalMaterial(int id, EducationalMaterial educationalMaterial);

    EducationalMaterial makeReadyForReview(EducationalMaterial material);

    EducationalMaterial reviewMaterial(EducationalMaterial material);

    EducationalMaterial backToReworkMaterial(EducationalMaterial material);

    EducationalMaterial cancelMaterial(EducationalMaterial material);

    EducationalMaterial approveMaterial(EducationalMaterial material);
}
