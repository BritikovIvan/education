package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.Student;

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

    private static final String SELECT_BY_GROUP = "select id, `group` from students where `group` = ?";

    @Override
    public Long insertStudent(Student student) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT))
        {
            ptmt.setLong(1, student.getId());
            ptmt.setInt(2, student.getGroup());
            ptmt.executeUpdate();
            return student.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteStudent(String studentId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, studentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Student findStudent(String studentId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
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
    public boolean updateStudent(Student student) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            ptmt.setInt(1, student.getGroup());
            ptmt.setLong(2, student.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<Student> selectStudents() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<Student> students = new ArrayList<>();
            Student studentsBean;
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

    @Override
    public Collection<Student> selectStudentsByGroup(int group) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_GROUP))
        {
            ptmt.setInt(1, group);
            List<Student> students = new ArrayList<>();
            Student studentsBean;
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
        student.setId(rs.getLong(1));
        student.setGroup(rs.getInt(2));
        return student;
    }
}
