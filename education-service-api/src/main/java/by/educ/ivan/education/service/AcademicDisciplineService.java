package by.educ.ivan.education.service;

import by.educ.ivan.education.model.AcademicDiscipline;

import java.util.Collection;

public interface AcademicDisciplineService {

    AcademicDiscipline createAcademicDiscipline(AcademicDiscipline academicDiscipline);

    AcademicDiscipline editAcademicDiscipline(AcademicDiscipline academicDiscipline);

    Collection<AcademicDiscipline> getAllAcademicDisciplines();
}
