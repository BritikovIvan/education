package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;

import java.util.Collection;

public interface StudentDAO {
    int insertStudent(Student student);

    boolean deleteStudent(String student);

    Student findStudent(String studentId);

    boolean updateStudent(String studentId);

    Collection<Student> selectStudents();
}
