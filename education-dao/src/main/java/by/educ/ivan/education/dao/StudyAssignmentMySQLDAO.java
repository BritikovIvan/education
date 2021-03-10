package by.educ.ivan.education.dao;

import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.AssignmentStatus;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.StudyAssignment;
import by.educ.ivan.education.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StudyAssignmentMySQLDAO implements StudyAssignmentDAO {

    public StudyAssignmentMySQLDAO() {
    }

    @Override
    public int insertStudyAssignment(StudyAssignment studyAssignment) {
        String sql = "insert into study_assignments (name, educational_material, student, review_status," +
                " creation_date, due_date, grade, description, date_of_change, teacher) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
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
            return -1;
        }
    }

    @Override
    public boolean deleteStudyAssignment(String studyAssignmentId) {
        String sql = "delete from study_assignments where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studyAssignmentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public StudyAssignment findStudyAssignment(String studyAssignmentId) {
        String sql = "select id, name, educational_material, student, review_status, creation_date, due_date," +
                " grade, description, date_of_change, teacher from study_assignments where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studyAssignmentId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setAssignment(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateStudyAssignment(String studyAssignmentId, String studyAssignmentName) {
        String sql = "update study_assignments set name = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, studyAssignmentName);
            ptmt.setString(2, studyAssignmentId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<StudyAssignment> selectStudyAssignments() {
        String sql = "select id, name, educational_material, student, review_status, creation_date, due_date, grade," +
                " description, date_of_change, teacher from study_assignments;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<StudyAssignment> studyAssignments = new ArrayList<StudyAssignment>();
            StudyAssignment studyAssignmentBean;
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                studyAssignmentBean = setAssignment(rs);
                studyAssignments.add(studyAssignmentBean);
            }
            return studyAssignments;
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

    private StudyAssignment setAssignment(ResultSet rs) throws SQLException {
        StudyAssignment studyAssignment = new StudyAssignment();
        UserDAO userDAO = new UserMySQLDAO();
        EducationalMaterialDAO materialDAO = new EducationalMaterialMySQLDAO();
        studyAssignment.setId(rs.getInt(1));
        studyAssignment.setName(rs.getString(2));
        EducationalMaterial material = materialDAO.findEducationalMaterial(rs.getString(3));
        studyAssignment.setEducationalMaterial(material);
        User student = userDAO.findUser(rs.getString(4));
        studyAssignment.setStudent(student);
        studyAssignment.setReviewStatus(AssignmentStatus.valueOf(rs.getString(5)));
        studyAssignment.setCreationDate(rs.getDate(6));
        studyAssignment.setDueDate(rs.getDate(7));
        studyAssignment.setGrade(rs.getInt(8));
        studyAssignment.setDescription(rs.getString(9));
        studyAssignment.setDateOfChange(rs.getDate(10));
        User teacher = userDAO.findUser(rs.getString(11));
        studyAssignment.setTeacher(teacher);
        return studyAssignment;
    }
}
