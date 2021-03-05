package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AcademicDiscipline;

import java.util.Collection;

public interface AcademicDisciplineDAO {
    int insertAcademicDiscipline(AcademicDiscipline academicDiscipline);

    boolean deleteAcademicDiscipline(String academicDiscipline);

    AcademicDiscipline findAcademicDiscipline(String academicDisciplineId);

    boolean updateAcademicDiscipline(String academicDisciplineId);

    Collection<AcademicDiscipline> selectAcademicDisciplines();
}
