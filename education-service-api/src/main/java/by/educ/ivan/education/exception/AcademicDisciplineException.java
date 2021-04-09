package by.educ.ivan.education.exception;

public class AcademicDisciplineException extends RuntimeException {

    public AcademicDisciplineException(String message) {
        super(message);
    }

    public AcademicDisciplineException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcademicDisciplineException(Throwable cause) {
        super(cause);
    }
}
