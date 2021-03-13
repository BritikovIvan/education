package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.AdminOperationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminOperationMySQLDAO implements AdminOperationDAO {

    private static final String INSERT = "insert into admin_operations (admin, operation, user, date, reason) " +
            "values (?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from admin_operations where id = ?";

    private static final String SELECT = "select id, admin, operation, user, date, reason from admin_operations where id = ?";

    private static final String UPDATE = "update admin_operations set operation = ? where id = ?";

    private static final String SELECT_ALL = "select id, admin, operation, user, date, reason from admin_operations";

    private final UserDAO userDAO;

    public AdminOperationMySQLDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int insertAdminOperation(AdminOperation adminOperation) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
            ptmt.setInt(1, adminOperation.getAdmin().getId());
            ptmt.setString(2, adminOperation.getType().name());
            ptmt.setInt(3, adminOperation.getUser().getId());
            ptmt.setDate(4, convert(adminOperation.getDate()));
            ptmt.setString(5, adminOperation.getReason());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteAdminOperation(String adminOperationId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, adminOperationId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public AdminOperation findAdminOperation(String adminOperationId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
            ptmt.setString(1, adminOperationId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setOperation(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateAdminOperation(String adminOperationId, String adminOperationType) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, adminOperationType);
            ptmt.setString(2, adminOperationId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<AdminOperation> selectAdminOperations() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<AdminOperation> adminOperations = new ArrayList<>();
            AdminOperation adminOperationBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                adminOperationBean = setOperation(rs);
                adminOperations.add(adminOperationBean);
            }
            return adminOperations;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    private AdminOperation setOperation(ResultSet rs) throws SQLException {
        AdminOperation adminOperation = new AdminOperation();
        adminOperation.setId(rs.getInt(1));
        adminOperation.setAdmin(userDAO.findUser(rs.getString(2)));
        adminOperation.setType(AdminOperationType.valueOf(rs.getString(3)));
        adminOperation.setUser(userDAO.findUser(rs.getString(4)));
        adminOperation.setDate(rs.getDate(5));
        adminOperation.setReason(rs.getString(6));
        return adminOperation;
    }

}
