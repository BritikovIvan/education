package by.educ.ivan.education.service;

import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.StudyAssignment;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StudyAssignmentService {

    Collection<StudyAssignment> getAllTeacherAssignments();

    Collection<StudyAssignment> createStudyAssignments(EducationalMaterial material, int group, String description, LocalDateTime dueDate);

    StudyAssignment editStudyAssignment(int id, StudyAssignment studyAssignment);

    Collection<StudyAssignment> getAllStudentAssignments();

    StudyAssignment makeReadyForReview(StudyAssignment studyAssignment);

    StudyAssignment reviewAssignment(StudyAssignment studyAssignment);

    StudyAssignment backToReworkAssignment(StudyAssignment studyAssignment);

    StudyAssignment cancelAssignment(StudyAssignment studyAssignment);

    StudyAssignment gradeAssignment(StudyAssignment studyAssignment);
}
