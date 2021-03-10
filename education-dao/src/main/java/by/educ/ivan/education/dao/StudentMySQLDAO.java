package by.educ.ivan.education.dao;

import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StudentMySQLDAO implements StudentDAO {

    public StudentMySQLDAO() {
    }

    @Override
    public int insertStudent(Student student) {
        String sql = "insert into students (id, `group`) values (?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setInt(1, student.getUser().getId());
            ptmt.setInt(2, student.getGroup());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        String sql = "delete from students where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Student findStudent(String studentId) {
        String sql = "select id, `group` from students where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studentId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setStudent(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateStudent(String studentId, String studentGroup) {
        String sql = "update students set `group` = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studentGroup);
            ptmt.setString(2, studentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<Student> selectStudents() {
        String sql = "select id, `group` from students;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<Student> students = new ArrayList<Student>();
            Student studentsBean;
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studentsBean = setStudent(rs);
                students.add(studentsBean);
            }
            return students;
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }

    private Student setStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        UserDAO userDAO = new UserMySQLDAO();
        User user = userDAO.findUser(rs.getString(1));
        student.setUser(user);
        student.setGroup(rs.getInt(2));
        return student;
    }
}
