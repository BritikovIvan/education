package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.Student;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentMySQLDAO implements StudentDAO {

    private static final String INSERT = "insert into students (id, `group`) values (?, ?)";

    private static final String DELETE = "delete from students where id = ?";

    private static final String SELECT = "select id, `group` from students where id = ?";

    private static final String UPDATE = "update students set `group` = ? where id = ?";

    private static final String SELECT_ALL = "select id, `group` from students";

    private final UserDAO userDAO;

    public StudentMySQLDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int insertStudent(Student student) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
            ptmt.setInt(1, student.getUser().getId());
            ptmt.setInt(2, student.getGroup());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, studentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Student findStudent(String studentId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
            ptmt.setString(1, studentId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setStudent(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateStudent(String studentId, String studentGroup) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, studentGroup);
            ptmt.setString(2, studentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<Student> selectStudents() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<Student> students = new ArrayList<>();
            Student studentsBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studentsBean = setStudent(rs);
                students.add(studentsBean);
            }
            return students;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private Student setStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setUser(userDAO.findUser(rs.getString(1)));
        student.setGroup(rs.getInt(2));
        return student;
    }
}
