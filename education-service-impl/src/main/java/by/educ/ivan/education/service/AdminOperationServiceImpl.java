package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.AdminOperationDAO;
import by.educ.ivan.education.exception.AdminOperationException;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

import java.time.LocalDateTime;

public class AdminOperationServiceImpl implements AdminOperationService {

    private final UserService userService;

    private final AdminOperationDAO operationDAO;

    private final SessionService sessionService;

    public AdminOperationServiceImpl(UserService userService, AdminOperationDAO operationDAO, SessionService sessionService) {
        this.userService = userService;
        this.operationDAO = operationDAO;
        this.sessionService = sessionService;
    }

    @Override
    public AdminOperation createMagistralStaff(AdminOperation adminOperation) {
        if (sessionService.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AdminOperationException("Wrong admin user role.");
        } else if (userService.isMagistralStaff(adminOperation.getUser())) {
            throw new AdminOperationException("Wrong user role.");
        }
        userService.createUser(adminOperation.getUser());
        return createAdminOperation(adminOperation);
    }


    @Override
    public AdminOperation blockUser(AdminOperation adminOperation) {
        if (sessionService.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AdminOperationException("Wrong admin user role.");
        } else if (userService.isUserBlocked(adminOperation.getUser())) {
            throw new AdminOperationException("This user is already blocked.");
        }
        userService.updateUserStatus(adminOperation.getUser(), true);
        return createAdminOperation(adminOperation);
    }

    @Override
    public AdminOperation unblockUser(AdminOperation adminOperation) {
        if (sessionService.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AdminOperationException("Wrong admin user role.");
        } else if (!userService.isUserBlocked(adminOperation.getUser())) {
            throw new AdminOperationException("This user is already unblocked.");
        }
        userService.updateUserStatus(adminOperation.getUser(), false);
        return createAdminOperation(adminOperation);
    }

    @Override
    public AdminOperation changeRole(AdminOperation adminOperation, Role newRole) {
        if (sessionService.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AdminOperationException("Wrong admin user role.");
        } else if (isRoleChanged(adminOperation.getUser())) {
            throw new AdminOperationException("This user already has this role.");
        }
        userService.updateUserRole(adminOperation.getUser(), newRole);
        return createAdminOperation(adminOperation);
    }

    private boolean isRoleChanged(User user) {
        return userService.getUser(user.getId()).getRole() == user.getRole();
    }

    private AdminOperation createAdminOperation(AdminOperation adminOperation){
        adminOperation.setDate(LocalDateTime.now());
        adminOperation.setAdmin(sessionService.getCurrentUser());
        adminOperation.setId(operationDAO.insertAdminOperation(adminOperation));
        return adminOperation;
    }
}
