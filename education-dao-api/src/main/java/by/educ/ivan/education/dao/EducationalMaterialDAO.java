package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface EducationalMaterialDAO {
    Long insertEducationalMaterial(EducationalMaterial educationalMaterial);

    boolean deleteEducationalMaterial(String educationalMaterialId);

    EducationalMaterial findEducationalMaterial(String educationalMaterialId);

    Collection<EducationalMaterial> selectEducationalMaterials();

    Collection<EducationalMaterial> selectEducationalMaterialsByReviewer(User professor);

    Collection<EducationalMaterial> selectEducationalMaterialsByTeacher(User teacher);

    boolean updateEducationalMaterial(EducationalMaterial material);

    Collection<EducationalMaterial> selectEducationalMaterialsByDiscipline(AcademicDiscipline discipline);
}
