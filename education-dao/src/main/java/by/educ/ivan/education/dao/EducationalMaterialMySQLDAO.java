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

public class EducationalMaterialMySQLDAO implements EducationalMaterialDAO {

    public EducationalMaterialMySQLDAO() {
    }

    @Override
    public int insertEducationalMaterial(EducationalMaterial educationalMaterial) {
        String sql = "insert into educational_materials (name, academic_discipline, author, reviewer, review_status, creation_date," +
                " type, review_finish_date, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, educationalMaterial.getName());
            ptmt.setInt(2, educationalMaterial.getAcademicDiscipline().getId());
            ptmt.setInt(3, educationalMaterial.getAuthor().getId());
            ptmt.setInt(4, educationalMaterial.getReviewer().getId());
            ptmt.setString(5, educationalMaterial.getReviewStatus().name());
            ptmt.setDate(6, convert(educationalMaterial.getCreationDate()));
            ptmt.setString(7, educationalMaterial.getType().name());
            ptmt.setDate(8, convert(educationalMaterial.getReviewFinishDate()));
            ptmt.setString(9, educationalMaterial.getDescription());
            return ptmt.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public boolean deleteEducationalMaterial(String educationalMaterialId) {
        String sql = "delete from educational_materials where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, educationalMaterialId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public EducationalMaterial findEducationalMaterial(String educationalMaterialId) {
        String sql = "select id, name, academic_discipline, author, reviewer, review_status, creation_date," +
                " type, review_finish_date, description from educational_materials where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, educationalMaterialId);
            ResultSet rs = ptmt.executeQuery();
            rs.next();
            return setMaterial(rs);
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean updateEducationalMaterial(String educationalMaterialId, String educationalMaterialName) {
        String sql = "update educational_materials set name = ? where id = ?;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ptmt.setString(1, educationalMaterialName);
            ptmt.setString(2, educationalMaterialId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterials() {
        String sql = "select id, name, academic_discipline, author, reviewer, review_status, creation_date," +
                " type, review_finish_date, description from educational_materials;";

        try(Connection connection = MySQLDAOFactory.getConnection()) {
            List<EducationalMaterial> educationalMaterials = new ArrayList<EducationalMaterial>();
            EducationalMaterial educationalMaterialBean;
            PreparedStatement ptmt = connection.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                educationalMaterialBean = setMaterial(rs);
                educationalMaterials.add(educationalMaterialBean);
            }
            return educationalMaterials;
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

    private EducationalMaterial setMaterial(ResultSet rs) throws SQLException {
        EducationalMaterial educationalMaterial = new EducationalMaterial();
        UserDAO userDAO = new UserMySQLDAO();
        AcademicDisciplineDAO disciplineDAO = new AcademicDisciplineMySQLDAO();
        educationalMaterial.setId(rs.getInt(1));
        educationalMaterial.setName(rs.getString(2));
        AcademicDiscipline academicDiscipline = disciplineDAO.findAcademicDiscipline(rs.getString(3));
        educationalMaterial.setAcademicDiscipline(academicDiscipline);
        User author = userDAO.findUser(rs.getString(4));
        educationalMaterial.setAuthor(author);
        User reviewer = userDAO.findUser(rs.getString(5));
        educationalMaterial.setReviewer(reviewer);
        educationalMaterial.setReviewStatus(MaterialStatus.valueOf(rs.getString(6)));
        educationalMaterial.setCreationDate(rs.getDate(7));
        educationalMaterial.setType(MaterialType.valueOf(rs.getString(8)));
        educationalMaterial.setReviewFinishDate(rs.getDate(9));
        educationalMaterial.setDescription(rs.getString(10));
        return educationalMaterial;
    }
}
