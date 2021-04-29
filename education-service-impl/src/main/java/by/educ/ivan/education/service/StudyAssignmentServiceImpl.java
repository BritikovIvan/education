package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.StudyAssignmentDAO;
import by.educ.ivan.education.exception.StudyAssignmentException;
import by.educ.ivan.education.model.AssignmentStatus;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.Student;
import by.educ.ivan.education.model.StudyAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class StudyAssignmentServiceImpl implements StudyAssignmentService {

    private final StudyAssignmentDAO assignmentDAO;

    private final SessionService sessionService;

    private final UserService userService;

    private final StudentService studentService;

    @Autowired
    public StudyAssignmentServiceImpl(StudyAssignmentDAO assignmentDAO, SessionService sessionService, UserService userService, StudentService studentService) {
        this.assignmentDAO = assignmentDAO;
        this.sessionService = sessionService;
        this.userService = userService;
        this.studentService = studentService;
    }

    @Override
    public Collection<StudyAssignment> getAllTeacherAssignments() {
        if (!userService.isTeacher()) {
            throw new StudyAssignmentException("Wrong teacher user role.");
        }

        return assignmentDAO.selectStudyAssignmentsByAuthor(sessionService.getCurrentUser());
    }

    @Override
    public Collection<StudyAssignment> createStudyAssignments(EducationalMaterial material, int group, String description, LocalDateTime dueDate) {
        if (!userService.isTeacher()) {
            throw new StudyAssignmentException("Wrong teacher user role.");
        }

        Collection<Student> students = studentService.getStudentsByGroup(group);
        Collection<StudyAssignment> studyAssignments = new ArrayList<>();
        int number = 1;
        for (Student student : students) {
            studyAssignments.add(createStudyAssignment(material, number, dueDate, description, student));
            number++;
        }
        return studyAssignments;
    }

    private StudyAssignment createStudyAssignment(EducationalMaterial material, int id, LocalDateTime dueDate, String description, Student student) {
        StudyAssignment studyAssignment = new StudyAssignment();
        String assignmentName = material.getAcademicDiscipline().getName() + id;
        studyAssignment.setName(assignmentName);
        studyAssignment.setReviewStatus(AssignmentStatus.OPEN);
        studyAssignment.setCreationDate(LocalDateTime.now());
        studyAssignment.setDueDate(dueDate);
        studyAssignment.setDescription(description);
        studyAssignment.setEducationalMaterial(material);
        studyAssignment.setTeacher(sessionService.getCurrentUser());
        studyAssignment.setStudent(student);
        studyAssignment.setId(assignmentDAO.insertStudyAssignment(studyAssignment));
        return studyAssignment;
    }

    @Override
    public StudyAssignment editStudyAssignment(int id, StudyAssignment studyAssignment) {
        if (isNotAuthor(studyAssignment)) {
            throw new StudyAssignmentException("The user doesnt have access to this action.");
        }

        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment.getId()));
        if (studyAssignment.getDueDate() != null) {
            assignment.setDueDate(studyAssignment.getDueDate());
        }
        if (studyAssignment.getGrade() != 0) {
            assignment.setGrade(studyAssignment.getGrade());
        }
        if (studyAssignment.getDescription() != null) {
            assignment.setDescription(studyAssignment.getDescription());
        }
        if (studyAssignment.getEducationalMaterial() != null) {
            assignment.setEducationalMaterial(studyAssignment.getEducationalMaterial());
        }
        assignmentDAO.updateStudyAssignment(assignment);
        return assignment;
    }

    private boolean isNotAuthor(StudyAssignment studyAssignment) {
        return sessionService.getCurrentUser().equals(studyAssignment.getTeacher());
    }

    @Override
    public Collection<StudyAssignment> getAllStudentAssignments() {
        if (!studentService.isStudent()) {
            throw new StudyAssignmentException("Wrong student user role.");
        }

        return assignmentDAO.selectStudyAssignmentsByStudent(studentService.findStudent(sessionService.getCurrentUser().getId()));
    }

    @Override
    public StudyAssignment makeReadyForReview(StudyAssignment studyAssignment) {
        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment.getId()));
        if (isReadyForReviewReached(assignment)) {
            if (isNotAssignmentmaker(assignment)) {
                throw new StudyAssignmentException("The user doesnt have access to this action.");
            }
            assignment.setReviewStatus(AssignmentStatus.READY_FOR_REVIEW);
            assignmentDAO.updateStudyAssignment(assignment);
            return assignment;
        } else {
            throw new StudyAssignmentException("Assignment with this status cannot be ready for review.");
        }
    }

    private boolean isNotAssignmentmaker(StudyAssignment assignment) {
        return sessionService.getCurrentUser().equals(assignment.getStudent());
    }

    private boolean isReadyForReviewReached(StudyAssignment assignment) {
        return assignment.getReviewStatus() == AssignmentStatus.OPEN || assignment.getReviewStatus() == AssignmentStatus.BACK_TO_REWORK;
    }

    @Override
    public StudyAssignment reviewAssignment(StudyAssignment studyAssignment) {
        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment.getId()));
        if (assignment.getReviewStatus() != AssignmentStatus.READY_FOR_REVIEW) {
            throw new StudyAssignmentException("Assignment with this status cannot be reviewed.");
        }
        if (isNotAuthor(assignment)) {
            throw new StudyAssignmentException("The user doesnt have access to this action.");
        }
        assignment.setReviewStatus(AssignmentStatus.UNDER_REVIEW);
        assignmentDAO.updateStudyAssignment(assignment);
        return assignment;
    }

    @Override
    public StudyAssignment backToReworkAssignment(StudyAssignment studyAssignment) {
        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment.getId()));
        if (assignment.getReviewStatus() != AssignmentStatus.UNDER_REVIEW) {
            throw new StudyAssignmentException("Assignment with this status cannot be back to rework.");
        }
        if (isNotAuthor(assignment)) {
            throw new StudyAssignmentException("The user doesnt have access to this action.");
        }
        assignment.setReviewStatus(AssignmentStatus.BACK_TO_REWORK);
        assignmentDAO.updateStudyAssignment(assignment);
        return assignment;
    }

    @Override
    public StudyAssignment cancelAssignment(StudyAssignment studyAssignment) {
        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment));
        if (mayBeCancelled(assignment)) {
            if (isNotAuthor(assignment)) {
                throw new StudyAssignmentException("The user doesnt have access to this action.");
            }
            assignment.setReviewStatus(AssignmentStatus.CANCELLED);
            assignmentDAO.updateStudyAssignment(assignment);
            return assignment;
        } else {
            throw new StudyAssignmentException("Assignment with this status cannot be cancelled.");
        }
    }

    private boolean mayBeCancelled(StudyAssignment assignment) {
        return assignment.getReviewStatus() == AssignmentStatus.OPEN || assignment.getReviewStatus() == AssignmentStatus.READY_FOR_REVIEW
                || assignment.getReviewStatus() == AssignmentStatus.UNDER_REVIEW;
    }

    @Override
    public StudyAssignment gradeAssignment(StudyAssignment studyAssignment) {
        StudyAssignment assignment = assignmentDAO.findStudyAssignment(String.valueOf(studyAssignment.getId()));
        if (assignment.getReviewStatus() != AssignmentStatus.UNDER_REVIEW) {
            throw new StudyAssignmentException("Assignment with this status cannot be graded.");
        }
        if (isNotAuthor(assignment)) {
            throw new StudyAssignmentException("The user doesnt have access to this action.");
        }
        assignment.setReviewStatus(AssignmentStatus.ASSESSED);
        assignmentDAO.updateStudyAssignment(assignment);
        return assignment;
    }
}
