package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AssignmentStatus;
import by.educ.ivan.education.model.Student;
import by.educ.ivan.education.model.StudyAssignment;
import by.educ.ivan.education.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudyAssignmentMySQLDAO implements StudyAssignmentDAO {

    private static final String INSERT = "insert into study_assignments (name, educational_material, student, review_status, " +
            "creation_date, due_date, grade, description, date_of_change, teacher) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from study_assignments where id = ?";

    private static final String SELECT = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from study_assignments where id = ?";

    private static final String UPDATE = "update study_assignments set name = ?, educational_material = ?, student = ?, " +
            "review_status = ?, creation_date = ?, due_date = ?, grade = ?, description = ?, date_of_change = ?, " +
            "teacher = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from study_assignments";

    private static final String SELECT_BY_AUTHOR = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from study_assignments where teacher = ?";

    private static final String SELECT_BY_STUDENT = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from education.study_assignments where student = ?";

    private final UserDAO userDAO;

    private final StudentDAO studentDAO;

    private final EducationalMaterialDAO educationalMaterialDAO;

    public StudyAssignmentMySQLDAO(UserDAO userDAO, StudentDAO studentDAO, EducationalMaterialDAO educationalMaterialDAO) {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.educationalMaterialDAO = educationalMaterialDAO;
    }

    @Override
    public Long insertStudyAssignment(StudyAssignment studyAssignment) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            ptmt.setString(1, studyAssignment.getName());
            ptmt.setLong(2, studyAssignment.getEducationalMaterial().getId());
            ptmt.setLong(3, studyAssignment.getStudent().getId());
            ptmt.setString(4, studyAssignment.getReviewStatus().name());
            ptmt.setTimestamp(5, convertToDatabaseColumn(studyAssignment.getCreationDate()));
            ptmt.setTimestamp(6, convertToDatabaseColumn(studyAssignment.getDueDate()));
            ptmt.setInt(7, studyAssignment.getGrade());
            ptmt.setString(8, studyAssignment.getDescription());
            ptmt.setTimestamp(9, convertToDatabaseColumn(studyAssignment.getDateOfChange()));
            ptmt.setLong(10, studyAssignment.getTeacher().getId());
            int affectedRows = ptmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating study assignment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ptmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    studyAssignment.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating study assignment failed, no ID obtained.");
                }
            }
            return studyAssignment.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteStudyAssignment(String studyAssignmentId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, studyAssignmentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public StudyAssignment findStudyAssignment(String studyAssignmentId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
            ptmt.setString(1, studyAssignmentId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setAssignment(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateStudyAssignment(StudyAssignment studyAssignment) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            ptmt.setString(1, studyAssignment.getName());
            ptmt.setLong(2, studyAssignment.getEducationalMaterial().getId());
            ptmt.setLong(3, studyAssignment.getStudent().getId());
            ptmt.setString(4, studyAssignment.getReviewStatus().toString());
            ptmt.setTimestamp(5, convertToDatabaseColumn(studyAssignment.getCreationDate()));
            ptmt.setTimestamp(6, convertToDatabaseColumn(studyAssignment.getDueDate()));
            ptmt.setInt(7, studyAssignment.getGrade());
            ptmt.setString(8, studyAssignment.getDescription());
            ptmt.setTimestamp(9, convertToDatabaseColumn(studyAssignment.getDateOfChange()));
            ptmt.setLong(10, studyAssignment.getTeacher().getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignments() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<StudyAssignment> studyAssignments = new ArrayList<>();
            StudyAssignment studyAssignmentBean;
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studyAssignmentBean = setAssignment(rs);
                studyAssignments.add(studyAssignmentBean);
            }
            return studyAssignments;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignmentsByAuthor(User author) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_AUTHOR))
        {
            List<StudyAssignment> studyAssignments = new ArrayList<>();
            StudyAssignment studyAssignmentBean;
            ptmt.setLong(1, author.getId());
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studyAssignmentBean = setAssignment(rs);
                studyAssignments.add(studyAssignmentBean);
            }
            return studyAssignments;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignmentsByStudent(Student student) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_STUDENT))
        {
            List<StudyAssignment> studyAssignments = new ArrayList<>();
            StudyAssignment studyAssignmentBean;
            ptmt.setLong(1, student.getId());
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studyAssignmentBean = setAssignment(rs);
                studyAssignments.add(studyAssignmentBean);
            }
            return studyAssignments;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    private java.sql.Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    private StudyAssignment setAssignment(ResultSet rs) throws SQLException {
        StudyAssignment studyAssignment = new StudyAssignment();
        studyAssignment.setId(rs.getLong(1));
        studyAssignment.setName(rs.getString(2));
        studyAssignment.setEducationalMaterial(educationalMaterialDAO.findEducationalMaterial(rs.getString(3)));
        studyAssignment.setStudent(studentDAO.findStudent(rs.getString(4)));
        studyAssignment.setReviewStatus(AssignmentStatus.valueOf(rs.getString(5)));
        studyAssignment.setCreationDate(convertToModelAttribute(rs.getTimestamp(6)));
        studyAssignment.setDueDate(convertToModelAttribute(rs.getTimestamp(7)));
        studyAssignment.setGrade(rs.getInt(8));
        studyAssignment.setDescription(rs.getString(9));
        studyAssignment.setDateOfChange(convertToModelAttribute(rs.getTimestamp(10)));
        studyAssignment.setTeacher(userDAO.findUser(rs.getString(11)));
        return studyAssignment;
    }

    private LocalDateTime convertToModelAttribute(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
