package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.StudentDAO;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.Student;

import java.util.Collection;

public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    private final SessionService sessionService;

    public StudentServiceImpl(StudentDAO studentDAO, SessionService sessionService) {
        this.studentDAO = studentDAO;
        this.sessionService = sessionService;
    }

    @Override
    public Collection<Student> getStudentsByGroup(int group) {
        return studentDAO.selectStudentsByGroup(group);
    }

    @Override
    public boolean isStudent() {
        return sessionService.getCurrentUser().getRole() == Role.STUDENT;
    }

    @Override
    public Student findStudent(Long id) {
        return studentDAO.findStudent(String.valueOf(id));
    }
}
