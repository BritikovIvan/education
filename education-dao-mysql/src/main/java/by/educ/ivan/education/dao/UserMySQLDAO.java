package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMySQLDAO implements UserDAO {

    private static final String INSERT = "insert into users (full_name, email, password, role, blocked) values (?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from users where id = ?";

    private static final String SELECT = "select id, full_name, email, password, role, blocked from users where id = ?";

    private static final String UPDATE = "update users set full_name = ?, email = ?, password = ?, " +
            "role = ?, blocked = ? where id = ?";

    private static final String SELECT_ALL = "select id, full_name, email, password, role, blocked from users";

    private static final String SELECT_BY_EMAIL = "select id, full_name, email, password, role, blocked from users where email = ?";

    public UserMySQLDAO() {
    }

    @Override
    public Long insertUser(User user) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            setJdbcUser(user, ptmt);
            int affectedRows = ptmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ptmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating user failed, no ID obtained.");
                }
            }
            return user.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, userId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public User findUser(String userId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
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
    public boolean updateUser(User user) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            setJdbcUser(user, ptmt);
            ptmt.setLong(6, user.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private void setJdbcUser(User user, PreparedStatement ptmt) throws SQLException {
        ptmt.setString(1, user.getFullName());
        ptmt.setString(2, user.getEmail());
        ptmt.setString(3, user.getPassword());
        ptmt.setString(4, user.getRole().toString());
        ptmt.setInt(5, user.isBlocked() ? 1 : 0);
    }

    @Override
    public Collection<User> selectUsers() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<User> users = new ArrayList<>();
            User userBean;
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
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_EMAIL))
        {
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

    private User setUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setFullName(rs.getString(2));
        user.setEmail(rs.getString(3));
        user.setPassword(rs.getString(4));
        user.setRole(Role.valueOf(rs.getString(5)));
        user.setBlocked(rs.getInt(6) == 1);
        return user;
    }
}
