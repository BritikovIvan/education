package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;

import java.util.Collection;

public interface AcademicDisciplineDAO {
    AcademicDiscipline insertAcademicDiscipline(AcademicDiscipline academicDiscipline);

    boolean deleteAcademicDiscipline(String academicDisciplineId);

    AcademicDiscipline findAcademicDiscipline(String academicDisciplineId);

    boolean updateAcademicDiscipline(AcademicDiscipline academicDiscipline);

    Collection<AcademicDiscipline> selectAcademicDisciplines();
}
