package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;

import java.util.Collection;

public interface StudentDAO {
    int insertStudent(Student student);

    boolean deleteStudent(String studentId);

    Student findStudent(String studentId);

    boolean updateStudent(String studentId, String studentGroup);

    Collection<Student> selectStudents();
}
