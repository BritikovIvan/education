package by.educ.ivan.education.service;

import by.educ.ivan.education.dao.AdminOperationDAO;
import by.educ.ivan.education.exception.AdminOperationException;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

public class AdminOperationServiceImpl implements AdminOperationService {

    private final UserService userService;

    private final AdminOperationDAO operationDAO;

    public AdminOperationServiceImpl(UserService userService, AdminOperationDAO operationDAO) {
        this.userService = userService;
        this.operationDAO = operationDAO;
    }

    @Override
    public void addAdminOperation(AdminOperation adminOperation) throws AdminOperationException {
        if (adminOperation.getAdmin().getRole() != Role.ADMIN) {
            throw new AdminOperationException("Wrong admin user role.");
        }

        switch (adminOperation.getType()) {
            case CREATE:
                createMagistralStaff(adminOperation);
            case BLOCK:
                blockUser(adminOperation);
            case UNBLOCK:
                unblockUser(adminOperation);
            case CHANGE_ROLE:
                changeRole(adminOperation);
        }

        operationDAO.insertAdminOperation(adminOperation);
    }

    private void createMagistralStaff(AdminOperation adminOperation) throws AdminOperationException {
        if (isMagistralStaff(adminOperation.getUser())) {
            throw new AdminOperationException("Wrong user role.");
        }
        userService.createUser(adminOperation.getUser());
    }

    private boolean isMagistralStaff(User user) {
        return user.getRole() == Role.PROFESSOR || user.getRole() == Role.TEACHER;
    }

    private void blockUser(AdminOperation adminOperation) throws AdminOperationException {
        if (isUserBlocked(adminOperation.getUser())) {
            throw new AdminOperationException("This user is already blocked.");
        }
        userService.updateUserStatus(adminOperation.getUser());
    }

    private boolean isUserBlocked(User user) {
        return userService.getUserByEmail(user.getEmail()).isBlocked();
    }

    private void unblockUser(AdminOperation adminOperation) throws AdminOperationException {
        if (!isUserBlocked(adminOperation.getUser())) {
            throw new AdminOperationException("This user is already unblocked.");
        }
        userService.updateUserStatus(adminOperation.getUser());
    }

    private void changeRole(AdminOperation adminOperation) throws AdminOperationException {
        if (isRoleChanged(adminOperation.getUser())) {
            throw new AdminOperationException("This user already has this role.");
        }
        userService.updateUserRole(adminOperation.getUser());
    }

    private boolean isRoleChanged(User user) {
        return userService.getUserByEmail(user.getEmail()).getRole() == user.getRole();
    }
}
