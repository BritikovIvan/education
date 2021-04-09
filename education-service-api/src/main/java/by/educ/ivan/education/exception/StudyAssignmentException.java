package by.educ.ivan.education.exception;

public class StudyAssignmentException extends RuntimeException {
    public StudyAssignmentException(String message) {
        super(message);
    }

    public StudyAssignmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudyAssignmentException(Throwable cause) {
        super(cause);
    }
}
