package by.educ.ivan.education.dao;

import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.Role;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserMySQLDAO implements UserDAO {

    public UserMySQLDAO() {
    }

    @Override
    public int insertUser(User user) {
        String sql = "insert into users (full_name, email, password, role, blocked) values (?, ?, ?, ?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, user.getFullName());
            ptmt.setString(2, user.getEmail());
            ptmt.setString(3, user.getPassword());
            ptmt.setString(4, user.getRole().toString());
            ptmt.setInt(5, user.isBlocked() ? 1 : 0);
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        String sql = "delete from users where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, userId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public User findUser(String userId) {
        String sql = "select id, full_name, email, password, role, blocked from users where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, userId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setUser(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateUser(String userId, String userFullName) {
        String sql = "update users set full_name = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, userFullName);
            ptmt.setString(2, userId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<User> selectUsers() {
        String sql = "select id, full_name, email, password, role, blocked from users;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<User> users = new ArrayList<User>();
            User userBean;
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                userBean = setUser(rs);
                users.add(userBean);
            }
            return users;
        } catch (SQLException ex) {
            return Collections.emptyList();
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
