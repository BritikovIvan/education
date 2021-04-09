package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.Student;
import by.educ.ivan.education.model.StudyAssignment;
import by.educ.ivan.education.model.User;

import java.util.Collection;

public interface StudyAssignmentDAO {

    Long insertStudyAssignment(StudyAssignment studyAssignment);

    boolean deleteStudyAssignment(String studyAssignmentId);

    StudyAssignment findStudyAssignment(String studyAssignmentId);

    boolean updateStudyAssignment(StudyAssignment studyAssignment);

    Collection<StudyAssignment> selectStudyAssignments();

    Collection<StudyAssignment> selectStudyAssignmentsByAuthor(User author);

    Collection<StudyAssignment> selectStudyAssignmentsByStudent(Student student);
}
