package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.StudyAssignment;

import java.util.Collection;

public interface StudyAssignmentDAO {
    int insertStudyAssignment(StudyAssignment studyAssignment);

    boolean deleteStudyAssignment(String studyAssignmentId);

    StudyAssignment findStudyAssignment(String studyAssignmentId);

    boolean updateStudyAssignment(String studyAssignmentId, String studyAssignmentName);

    Collection<StudyAssignment> selectStudyAssignments();
}
