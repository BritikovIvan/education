package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.EducationalMaterial;

import java.util.Collection;

public interface EducationalMaterialDAO {
    int insertEducationalMaterial(EducationalMaterial educationalMaterial);

    boolean deleteEducationalMaterial(String educationalMaterialId);

    EducationalMaterial findEducationalMaterial(String educationalMaterialId);

    boolean updateEducationalMaterial(String educationalMaterialId, String educationalMaterialName);

    Collection<EducationalMaterial> selectEducationalMaterials();
}