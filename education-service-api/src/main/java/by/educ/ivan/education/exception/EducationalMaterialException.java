package by.educ.ivan.education.exception;

public class EducationalMaterialException extends RuntimeException {
    public EducationalMaterialException(String message) {
        super(message);
    }

    public EducationalMaterialException(String message, Throwable cause) {
        super(message, cause);
    }

    public EducationalMaterialException(Throwable cause) {
        super(cause);
    }
}
