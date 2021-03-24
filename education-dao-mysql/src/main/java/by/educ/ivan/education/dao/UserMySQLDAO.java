package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMySQLDAO implements UserDAO {

    private static final String INSERT = "insert into users (full_name, email, password, role, blocked) values (?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from users where id = ?";

    private static final String SELECT = "select id, full_name, email, password, role, blocked from users where id = ?";

    private static final String UPDATE = "update users set full_name = ? where id = ?";

    private static final String SELECT_ALL = "select id, full_name, email, password, role, blocked from users";

    private static final String SELECT_BY_EMAIL = "select id, full_name, email, password, role, blocked from users " +
            "where email = ?";

    private static final String UPDATE_STATUS = "update users set blocked = ? where id = ?";

    private static final String UPDATE_ROLE = "update users set role = ? where id = ?";

    public UserMySQLDAO() {
    }

    @Override
    public int insertUser(User user) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
            ptmt.setString(1, user.getFullName());
            ptmt.setString(2, user.getEmail());
            ptmt.setString(3, user.getPassword());
            ptmt.setString(4, user.getRole().toString());
            ptmt.setInt(5, user.isBlocked() ? 1 : 0);
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, userId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public User findUser(String userId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
            ptmt.setString(1, userId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setUser(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateUserName(String userId, String userFullName) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, userFullName);
            ptmt.setString(2, userId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<User> selectUsers() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<User> users = new ArrayList<>();
            User userBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                userBean = setUser(rs);
                users.add(userBean);
            }
            return users;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_EMAIL);
            ptmt.setString(1, email);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setUser(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateUserStatus(User user) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE_STATUS);
            ptmt.setBoolean(1, user.isBlocked());
            ptmt.setInt(2, user.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateUserRole(User user) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE_ROLE);
            ptmt.setString(1, user.getRole().toString());
            ptmt.setInt(2, user.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private User setUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setFullName(rs.getString(2));
        user.setEmail(rs.getString(3));
        user.setPassword(rs.getString(4));
        user.setRole(Role.valueOf(rs.getString(5)));
        user.setBlocked(rs.getInt(6) == 1);
        return user;
    }
}
