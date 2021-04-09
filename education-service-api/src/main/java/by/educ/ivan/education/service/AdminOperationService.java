package by.educ.ivan.education.service;

import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.Role;

public interface AdminOperationService {

    AdminOperation createMagistralStaff(AdminOperation adminOperation);

    AdminOperation blockUser(AdminOperation adminOperation);

    AdminOperation unblockUser(AdminOperation adminOperation);

    AdminOperation changeRole(AdminOperation adminOperation, Role newRole);
}
