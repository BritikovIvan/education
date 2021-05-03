package by.educ.ivan.education.dao;

import by.educ.ivan.education.exception.DaoException;
import by.educ.ivan.education.factory.MySQLDAOFactory;
import by.educ.ivan.education.model.*;

import java.sql.*;
import java.time.LocalDateTime;
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

    private static final String UPDATE = "update educational_materials set name = ?, academic_discipline = ?, " +
            "author = ?, reviewer = ?, review_status = ?, creation_date = ?, type = ?, review_finish_date = ?, " +
            "description = ? where id = ?";

    private static final String SELECT_ALL = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials";

    private static final String SELECT_BY_REVIEWER = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials where reviewer = ? and " +
            "review_status <> 'DRAFT' " +
            "union select id, name, academic_discipline, author, reviewer, review_status, creation_date, type, review_finish_date, " +
            "description from educational_materials where academic_discipline in " +
            "(select id from academic_disciplines where author = ?) and review_status <> 'DRAFT'";

    private static final String SELECT_BY_TEACHER = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials where author = ?";

    private static final String SELECT_BY_DISCIPLINE = "select id, name, academic_discipline, author, reviewer, review_status, " +
            "creation_date, type, review_finish_date, description from educational_materials where academic_discipline = ?";

    private final UserDAO userDAO;

    private final AcademicDisciplineDAO academicDisciplineDAO;

    public EducationalMaterialMySQLDAO(UserDAO userDAO, AcademicDisciplineDAO academicDisciplineDAO) {
        this.userDAO = userDAO;
        this.academicDisciplineDAO = academicDisciplineDAO;
    }

    @Override
    public EducationalMaterial insertEducationalMaterial(EducationalMaterial educationalMaterial) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            ptmt.setString(1, educationalMaterial.getName());
            ptmt.setLong(2, educationalMaterial.getAcademicDiscipline().getId());
            ptmt.setLong(3, educationalMaterial.getAuthor().getId());
            ptmt.setLong(4, educationalMaterial.getReviewer().getId());
            ptmt.setString(5, educationalMaterial.getReviewStatus().name());
            ptmt.setTimestamp(6, convertToDatabaseColumn(educationalMaterial.getCreationDate()));
            ptmt.setString(7, educationalMaterial.getType().name());
            ptmt.setTimestamp(8, convertToDatabaseColumn(educationalMaterial.getReviewFinishDate()));
            ptmt.setString(9, educationalMaterial.getDescription());
            int affectedRows = ptmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Creating educational material failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ptmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    educationalMaterial.setId(generatedKeys.getLong(1));
                } else {
                    throw new DaoException("Creating educational material failed, no ID obtained.");
                }
            }
            return educationalMaterial.getId();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean deleteEducationalMaterial(String educationalMaterialId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(DELETE))
        {
            ptmt.setString(1, educationalMaterialId);
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public EducationalMaterial findEducationalMaterial(String educationalMaterialId) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT))
        {
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
    public EducationalMaterial updateEducationalMaterial(EducationalMaterial educationalMaterial) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(UPDATE))
        {
            ptmt.setString(1, educationalMaterial.getName());
            ptmt.setLong(2, educationalMaterial.getAcademicDiscipline().getId());
            ptmt.setLong(3, educationalMaterial.getAuthor().getId());
            ptmt.setLong(4, educationalMaterial.getReviewer().getId());
            ptmt.setString(5, educationalMaterial.getReviewStatus().toString());
            ptmt.setTimestamp(6, convertToDatabaseColumn(educationalMaterial.getCreationDate()));
            ptmt.setString(7, educationalMaterial.getType().toString());
            ptmt.setTimestamp(8, convertToDatabaseColumn(educationalMaterial.getReviewFinishDate()));
            ptmt.setString(9, educationalMaterial.getDescription());
            ptmt.setLong(10, educationalMaterial.getId());
            int count = ptmt.executeUpdate();
            return count == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByDiscipline(AcademicDiscipline discipline) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_DISCIPLINE))
        {
            List<EducationalMaterial> educationalMaterials = new ArrayList<>();
            EducationalMaterial educationalMaterialBean;
            ptmt.setLong(1, discipline.getId());
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

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterials() {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_ALL))
        {
            List<EducationalMaterial> educationalMaterials = new ArrayList<>();
            EducationalMaterial educationalMaterialBean;
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

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByReviewer(User professor) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_REVIEWER))
        {
            List<EducationalMaterial> educationalMaterials = new ArrayList<>();
            EducationalMaterial educationalMaterialBean;
            ptmt.setLong(1, professor.getId());
            ptmt.setLong(2, professor.getId());
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

    @Override
    public Collection<EducationalMaterial> selectEducationalMaterialsByTeacher(User teacher) {
        try (Connection connection = MySQLDAOFactory.getConnection();
             PreparedStatement ptmt = connection.prepareStatement(SELECT_BY_TEACHER))
        {
            List<EducationalMaterial> educationalMaterials = new ArrayList<>();
            EducationalMaterial educationalMaterialBean;
            ptmt.setLong(1, teacher.getId());
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

    private java.sql.Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    private EducationalMaterial setMaterial(ResultSet rs) throws SQLException {
        EducationalMaterial educationalMaterial = new EducationalMaterial();
        educationalMaterial.setId(rs.getLong(1));
        educationalMaterial.setName(rs.getString(2));
        educationalMaterial.setAcademicDiscipline(academicDisciplineDAO.findAcademicDiscipline(rs.getString(3)));
        educationalMaterial.setAuthor(userDAO.findUser(rs.getString(4)));
        educationalMaterial.setReviewer(userDAO.findUser(rs.getString(5)));
        educationalMaterial.setReviewStatus(MaterialStatus.valueOf(rs.getString(6)));
        educationalMaterial.setCreationDate(convertToModelAttribute(rs.getTimestamp(7)));
        educationalMaterial.setType(MaterialType.valueOf(rs.getString(8)));
        educationalMaterial.setReviewFinishDate(convertToModelAttribute(rs.getTimestamp(9)));
        educationalMaterial.setDescription(rs.getString(10));
        return educationalMaterial;
    }

    private LocalDateTime convertToModelAttribute(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
