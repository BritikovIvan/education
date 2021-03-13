package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.EducationalMaterial;
import by.educ.ivan.education.model.MaterialStatus;
import by.educ.ivan.education.model.MaterialType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EducationalMaterialMySQLDAO implements EducationalMaterialDAO {

    private static final String INSERT = "insert into educational_materials " +
            "(name, academic_discipline, author, reviewer, review_status, creation_date, type, review_finish_date, description) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE = "delete from educational_materials where id = ?";

    private static final String SELECT = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials where id = ?";

    private static final String UPDATE = "update educational_materials set name = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials";

    private final UserDAO userDAO;

    private final AcademicDisciplineDAO academicDisciplineDAO;

    public EducationalMaterialMySQLDAO(UserDAO userDAO, AcademicDisciplineDAO academicDisciplineDAO) {
        this.userDAO = userDAO;
        this.academicDisciplineDAO = academicDisciplineDAO;
    }

    @Override
    public int insertEducationalMaterial(EducationalMaterial educationalMaterial) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(INSERT);
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
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteEducationalMaterial(String educationalMaterialId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(DELETE);
            ptmt.setString(1, educationalMaterialId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public EducationalMaterial findEducationalMaterial(String educationalMaterialId) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(SELECT);
            ptmt.setString(1, educationalMaterialId);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                return setMaterial(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean updateEducationalMaterial(String educationalMaterialId, String educationalMaterialName) {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            PreparedStatement ptmt = connection.prepareStatement(UPDATE);
            ptmt.setString(1, educationalMaterialName);
            ptmt.setString(2, educationalMaterialId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterials() {
        try (Connection connection = MySQLDAOFactory.getConnection()) {
            List<EducationalMaterial> educationalMaterials = new ArrayList<>();
            EducationalMaterial educationalMaterialBean;
            PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                educationalMaterialBean = setMaterial(rs);
                educationalMaterials.add(educationalMaterialBean);
            }
            return educationalMaterials;
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

    private EducationalMaterial setMaterial(ResultSet rs) throws SQLException {
        EducationalMaterial educationalMaterial = new EducationalMaterial();
        educationalMaterial.setId(rs.getInt(1));
        educationalMaterial.setName(rs.getString(2));
        educationalMaterial.setAcademicDiscipline(academicDisciplineDAO.findAcademicDiscipline(rs.getString(3)));
        educationalMaterial.setAuthor(userDAO.findUser(rs.getString(4)));
        educationalMaterial.setReviewer(userDAO.findUser(rs.getString(5)));
        educationalMaterial.setReviewStatus(MaterialStatus.valueOf(rs.getString(6)));
        educationalMaterial.setCreationDate(rs.getDate(7));
        educationalMaterial.setType(MaterialType.valueOf(rs.getString(8)));
        educationalMaterial.setReviewFinishDate(rs.getDate(9));
        educationalMaterial.setDescription(rs.getString(10));
        return educationalMaterial;
    }
}
