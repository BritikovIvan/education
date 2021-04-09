package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.AdminOperationType;

import java.sql.*;
import java.time.LocalDateTime;
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
    public Long insertAdminOperation(AdminOperation adminOperation) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            ptmt.setLong(1, adminOperation.getAdmin().getId());
            ptmt.setString(2, adminOperation.getType().name());
            ptmt.setLong(3, adminOperation.getUser().getId());
            ptmt.setTimestamp(4, convertToDatabaseColumn(adminOperation.getDate()));
            ptmt.setString(5, adminOperation.getReason());
            int affectedRows = ptmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating admin operation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ptmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    adminOperation.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating admin operation failed, no ID obtained.");
                }
            }
            return adminOperation.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteAdminOperation(String adminOperationId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, adminOperationId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public AdminOperation findAdminOperation(String adminOperationId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
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
    public boolean updateAdminOperation(AdminOperation adminOperation) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            ptmt.setString(1, adminOperation.getType().toString());
            ptmt.setLong(2, adminOperation.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<AdminOperation> selectAdminOperations() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<AdminOperation> adminOperations = new ArrayList<>();
            AdminOperation adminOperationBean;
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

    private java.sql.Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    private AdminOperation setOperation(ResultSet rs) throws SQLException {
        AdminOperation adminOperation = new AdminOperation();
        adminOperation.setId(rs.getLong(1));
        adminOperation.setAdmin(userDAO.findUser(rs.getString(2)));
        adminOperation.setType(AdminOperationType.valueOf(rs.getString(3)));
        adminOperation.setUser(userDAO.findUser(rs.getString(4)));
        adminOperation.setDate(convertToModelAttribute(rs.getTimestamp(5)));
        adminOperation.setReason(rs.getString(6));
        return adminOperation;
    }

    private LocalDateTime convertToModelAttribute(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

}
