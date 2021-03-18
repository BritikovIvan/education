package by.educ.ivan.education.dao;

import by.educ.ivan.education.model.AdminOperation;

import java.util.Collection;

public interface AdminOperationDAO {
    int insertAdminOperation(AdminOperation adminOperation);

    boolean deleteAdminOperation(String adminOperationId);

    AdminOperation findAdminOperation(String adminOperationId);

    boolean updateAdminOperation(String adminOperationId, String adminOperationType);

    Collection<AdminOperation> selectAdminOperations();
}
