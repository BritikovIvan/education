package by.educ.ivan.education.service;

import by.educ.ivan.education.exception.AdminOperationException;
import by.educ.ivan.education.model.AdminOperation;

public interface AdminOperationService {

    void addAdminOperation(AdminOperation adminOperation) throws AdminOperationException;
}
