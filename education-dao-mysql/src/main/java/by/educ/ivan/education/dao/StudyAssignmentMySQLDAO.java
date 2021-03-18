package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AssignmentStatus;
import by.educ.ivan.education.model.StudyAssignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudyAssignmentMySQLDAO implements StudyAssignmentDAO {

    private static final String INSERT = "insert into study_assignments (name, educational_material, student, review_status, " +
            "creation_date, due_date, grade, description, date_of_change, teacher) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from study_assignments where id = ?";

    private static final String SELECT = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from study_assignments where id = ?";

    private static final String UPDATE = "update study_assignments set name = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, educational_material, student, review_status, creation_date, " +
            "due_date, grade, description, date_of_change, teacher from study_assignments";

    private final UserDAO userDAO;

    private final EducationalMaterialDAO educationalMaterialDAO;

    public StudyAssignmentMySQLDAO(UserDAO userDAO, EducationalMaterialDAO educationalMaterialDAO) {
        this.userDAO = userDAO;
        this.educationalMaterialDAO = educationalMaterialDAO;
    }

    @Override
    public int insertStudyAssignment(StudyAssignment studyAssignment) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
            ptmt.setString(1, studyAssignment.getName());
            ptmt.setInt(2, studyAssignment.getEducationalMaterial().getId());
            ptmt.setInt(3, studyAssignment.getStudent().getId());
            ptmt.setString(4, studyAssignment.getReviewStatus().name());
            ptmt.setDate(5, convert(studyAssignment.getCreationDate()));
            ptmt.setDate(6, convert(studyAssignment.getDueDate()));
            ptmt.setInt(7, studyAssignment.getGrade());
            ptmt.setString(8, studyAssignment.getDescription());
            ptmt.setDate(9, convert(studyAssignment.getDateOfChange()));
            ptmt.setInt(10, studyAssignment.getTeacher().getId());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteStudyAssignment(String studyAssignmentId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, studyAssignmentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public StudyAssignment findStudyAssignment(String studyAssignmentId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
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
    public boolean updateStudyAssignment(String studyAssignmentId, String studyAssignmentName) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, studyAssignmentName);
            ptmt.setString(2, studyAssignmentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignments() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<StudyAssignment> studyAssignments = new ArrayList<>();
            StudyAssignment studyAssignmentBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
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

    private java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    private StudyAssignment setAssignment(ResultSet rs) throws SQLException {
        StudyAssignment studyAssignment = new StudyAssignment();
        studyAssignment.setId(rs.getInt(1));
        studyAssignment.setName(rs.getString(2));
        studyAssignment.setEducationalMaterial(educationalMaterialDAO.findEducationalMaterial(rs.getString(3)));
        studyAssignment.setStudent(userDAO.findUser(rs.getString(4)));
        studyAssignment.setReviewStatus(AssignmentStatus.valueOf(rs.getString(5)));
        studyAssignment.setCreationDate(rs.getDate(6));
        studyAssignment.setDueDate(rs.getDate(7));
        studyAssignment.setGrade(rs.getInt(8));
        studyAssignment.setDescription(rs.getString(9));
        studyAssignment.setDateOfChange(rs.getDate(10));
        studyAssignment.setTeacher(userDAO.findUser(rs.getString(11)));
        return studyAssignment;
    }
}
