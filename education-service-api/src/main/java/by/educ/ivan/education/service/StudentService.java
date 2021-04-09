package by.educ.ivan.education.service;

import by.educ.ivan.education.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getStudentsByGroup(int group);

    boolean isStudent();

    Student findStudent(Long id);
}
