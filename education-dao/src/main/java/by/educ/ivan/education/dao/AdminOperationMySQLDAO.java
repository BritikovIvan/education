package by.educ.ivan.education.dao;

import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AdminOperation;
import by.educ.ivan.education.model.AdminOperationType;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdminOperationMySQLDAO implements AdminOperationDAO {

    public AdminOperationMySQLDAO() {
    }

    @Override
    public int insertAdminOperation(AdminOperation adminOperation) {
        String sql = "insert into admin_operations (admin, operation, user, date, reason) values (?, ?, ?, ?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setInt(1, adminOperation.getAdmin().getId());
            ptmt.setString(2, adminOperation.getType().name());
            ptmt.setInt(3, adminOperation.getUser().getId());
            ptmt.setDate(4, convert(adminOperation.getDate()));
            ptmt.setString(5, adminOperation.getReason());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public boolean deleteAdminOperation(String adminOperationId) {
        String sql = "delete from admin_operations where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, adminOperationId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public AdminOperation findAdminOperation(String adminOperationId) {
        String sql = "select id, admin, operation, user, date, reason from admin_operations where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, adminOperationId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setOperation(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateAdminOperation(String adminOperationId, String adminOperationType) {
        String sql = "update admin_operations set operation = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, adminOperationType);
            ptmt.setString(2, adminOperationId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<AdminOperation> selectAdminOperations() {
        String sql = "select id, admin, operation, user, date, reason from admin_operations;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<AdminOperation> adminOperations = new ArrayList<AdminOperation>();
            AdminOperation adminOperationBean;
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                adminOperationBean = setOperation(rs);
                adminOperations.add(adminOperationBean);
            }
            return adminOperations;
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    private java.sql.Date convert(java.util.Date date) {
        if(date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    private AdminOperation setOperation(ResultSet rs) throws SQLException {
        AdminOperation adminOperation = new AdminOperation();
        UserDAO userDAO = new UserMySQLDAO();
        adminOperation.setId(rs.getInt(1));
        User admin = userDAO.findUser(rs.getString(2));
        adminOperation.setAdmin(admin);
        adminOperation.setType(AdminOperationType.valueOf(rs.getString(3)));
        User user = userDAO.findUser(rs.getString(4));
        adminOperation.setUser(user);
        adminOperation.setDate(rs.getDate(5));
        adminOperation.setReason(rs.getString(6));
        return adminOperation;
    }

}
