package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;

import java.util.Collection;

public interface StudentDAO {
    Long insertStudent(Student student);

    boolean deleteStudent(String studentId);

    Student findStudent(String studentId);

    boolean updateStudent(Student student);

    Collection<Student> selectStudents();

    Collection<Student> selectStudentsByGroup(int group);
}
